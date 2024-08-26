package com.cc.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.lease.model.entity.LeaseTerm;
import com.cc.lease.web.admin.service.LeaseTermService;
import com.cc.lease.web.admin.mapper.LeaseTermMapper;
import org.springframework.stereotype.Service;

/**
* @author cc
* @description 针对表【lease_term(租期)】的数据库操作Service实现
* @createDate 2024-07-24 15:48:00
*/
@Service
public class LeaseTermServiceImpl extends ServiceImpl<LeaseTermMapper, LeaseTerm>
    implements LeaseTermService{

}




