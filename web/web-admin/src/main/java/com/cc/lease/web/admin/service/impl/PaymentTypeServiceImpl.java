package com.cc.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.lease.model.entity.PaymentType;
import com.cc.lease.web.admin.service.PaymentTypeService;
import com.cc.lease.web.admin.mapper.PaymentTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author cc
* @description 针对表【payment_type(支付方式表)】的数据库操作Service实现
* @createDate 2024-07-24 15:48:00
*/
@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType>
    implements PaymentTypeService{

}




