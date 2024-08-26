package com.cc.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cc.lease.common.constant.RedisConstant;
import com.cc.lease.common.exception.LeaseException;
import com.cc.lease.common.result.ResultCodeEnum;
import com.cc.lease.common.utils.JwtUtil;
import com.cc.lease.common.utils.VerifyCodeUtil;
import com.cc.lease.model.entity.UserInfo;
import com.cc.lease.model.enums.BaseStatus;
import com.cc.lease.web.app.mapper.UserInfoMapper;
import com.cc.lease.web.app.service.LoginService;
import com.cc.lease.web.app.service.SmsService;
import com.cc.lease.web.app.vo.user.LoginVo;
import com.cc.lease.web.app.vo.user.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SmsService service;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void sendcode(String phone) {
        String code = VerifyCodeUtil.getVerifyCode(6);
        String key = RedisConstant.APP_LOGIN_PREFIX + phone;
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        if(hasKey){
            Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            if(RedisConstant.APP_LOGIN_CODE_TTL_SEC-expire<RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC){
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }
        service.sendcode(phone,code);
        stringRedisTemplate.opsForValue().set(key,code,RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);
    }

    @Override
    public String login(LoginVo loginVo) {
        //1.判断手机号码和验证码是否为空
        if(!StringUtils.hasText(loginVo.getPhone())){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }
        if(!StringUtils.hasText(loginVo.getCode())){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        }
        //2.校验验证码
        String key = RedisConstant.APP_LOGIN_PREFIX + loginVo.getPhone();
        String code = stringRedisTemplate.opsForValue().get(key);
        if (code == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }

        if (!code.equals(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone,loginVo.getPhone());
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if(userInfo==null) {
            userInfo = new UserInfo();
            userInfo.setPhone(loginVo.getPhone());
            userInfo.setStatus(BaseStatus.ENABLE);
            userInfo.setNickname("用户-" + loginVo.getPhone().substring(7));
            userInfoMapper.insert(userInfo);
        }
        if(userInfo.getStatus()==BaseStatus.DISABLE){
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }
        return JwtUtil.createToken(userInfo.getId(),userInfo.getPhone());
    }

    @Override
    public UserInfoVo getUserInfoById(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        UserInfoVo userInfoVo = new UserInfoVo(userInfo.getNickname(),userInfo.getAvatarUrl());
        return  userInfoVo;
    }
}
