package com.cc.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.lease.model.entity.SystemPost;
import com.cc.lease.model.entity.SystemUser;
import com.cc.lease.web.admin.mapper.SystemUserMapper;
import com.cc.lease.web.admin.service.SystemPostService;
import com.cc.lease.web.admin.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.cc.lease.web.admin.vo.system.user.SystemUserQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cc
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2024-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {
    @Autowired
    private SystemPostService systemPostService;
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Override
    public IPage<SystemUserItemVo> pageSystemUser(Page<SystemUserItemVo> page, SystemUserQueryVo queryVo) {
        return systemUserMapper.pageSystemUser(page,queryVo);
    }

    @Override
    public SystemUserItemVo getSystemUserById(Long id) {
        SystemUser systemUser = this.getById(id);
        SystemPost systemPost = systemPostService.getById(systemUser.getPostId());
        SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
        BeanUtils.copyProperties(systemUser,systemUserItemVo);
        systemUserItemVo.setPostName(systemPost.getName());
        return systemUserItemVo;
    }
}




