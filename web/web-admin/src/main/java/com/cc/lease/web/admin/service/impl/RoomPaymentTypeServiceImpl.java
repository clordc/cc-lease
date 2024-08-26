package com.cc.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.lease.model.entity.RoomPaymentType;
import com.cc.lease.web.admin.service.RoomPaymentTypeService;
import com.cc.lease.web.admin.mapper.RoomPaymentTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author cc
* @description 针对表【room_payment_type(房间&支付方式关联表)】的数据库操作Service实现
* @createDate 2024-07-24 15:48:00
*/
@Service
public class RoomPaymentTypeServiceImpl extends ServiceImpl<RoomPaymentTypeMapper, RoomPaymentType>
    implements RoomPaymentTypeService{

}




