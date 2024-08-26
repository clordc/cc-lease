package com.cc.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.lease.model.entity.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.cc.lease.web.admin.vo.system.user.SystemUserQueryVo;

/**
* @author cc
* @description 针对表【system_user(员工信息表)】的数据库操作Service
* @createDate 2024-07-24 15:48:00
*/
public interface SystemUserService extends IService<SystemUser> {

    IPage<SystemUserItemVo> pageSystemUser(Page<SystemUserItemVo> page, SystemUserQueryVo queryVo);

    SystemUserItemVo getSystemUserById(Long id);
}
