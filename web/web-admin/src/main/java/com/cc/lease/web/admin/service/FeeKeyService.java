package com.cc.lease.web.admin.service;

import com.cc.lease.model.entity.FeeKey;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.lease.web.admin.vo.fee.FeeKeyVo;

import java.util.List;

/**
* @author cc
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Service
* @createDate 2024-07-24 15:48:00
*/
public interface FeeKeyService extends IService<FeeKey> {

    List<FeeKeyVo> listFeeInfo();
}
