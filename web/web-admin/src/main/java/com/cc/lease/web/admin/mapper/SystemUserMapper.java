package com.cc.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.lease.model.entity.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.cc.lease.web.admin.vo.system.user.SystemUserQueryVo;

/**
* @author cc
* @description 针对表【system_user(员工信息表)】的数据库操作Mapper
* @createDate 2024-07-24 15:48:00
* @Entity com.cc.lease.model.SystemUser
*/
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    IPage<SystemUserItemVo> pageSystemUser(Page<SystemUserItemVo> page, SystemUserQueryVo queryVo);

    SystemUser selectOneByUserName(String username);
}




