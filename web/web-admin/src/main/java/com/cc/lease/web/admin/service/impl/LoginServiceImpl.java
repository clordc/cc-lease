package com.cc.lease.web.admin.service.impl;

import com.cc.lease.common.utils.JwtUtil;
import com.cc.lease.common.constant.RedisConstant;
import com.cc.lease.common.exception.LeaseException;
import com.cc.lease.common.result.ResultCodeEnum;
import com.cc.lease.model.entity.SystemUser;
import com.cc.lease.model.enums.BaseStatus;
import com.cc.lease.web.admin.mapper.SystemUserMapper;
import com.cc.lease.web.admin.service.LoginService;
import com.cc.lease.web.admin.vo.login.CaptchaVo;
import com.cc.lease.web.admin.vo.login.LoginVo;
import com.cc.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130,48,4);

        String code = specCaptcha.text().toLowerCase();
        String key = RedisConstant.APP_LOGIN_PREFIX + UUID.randomUUID();

        String image = specCaptcha.toBase64();
        stringRedisTemplate.opsForValue().set(key,code,RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);

        return new CaptchaVo(image,key);
    }

    @Override
    public String login(LoginVo loginVo) {
        //1.判断是否输入验证码
        if(!StringUtils.hasText(loginVo.getCaptchaCode())){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }
        //2.验证校验码
        String code = stringRedisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        if(code==null){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }
        if(!code.equals(loginVo.getCaptchaCode().toLowerCase())){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }
        //3.校验用户是否存在
        SystemUser user = systemUserMapper.selectOneByUserName(loginVo.getUsername());
        if(user==null){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }
        //4.校验用户是否禁用
        if(user.getStatus()== BaseStatus.DISABLE){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }
        //5.校验用户密码
        if(user.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }
        return JwtUtil.createToken(user.getId(),user.getUsername());
    }

    @Override
    public SystemUserInfoVo getSystemUserInfoVo(Long userId) {
        SystemUser systemUser = systemUserMapper.selectById(userId);
        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(systemUser.getName());
        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return systemUserInfoVo;
    }
}
