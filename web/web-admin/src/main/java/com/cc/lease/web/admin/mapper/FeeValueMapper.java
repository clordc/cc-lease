package com.cc.lease.web.admin.mapper;

import com.cc.lease.model.entity.FeeValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.lease.web.admin.vo.fee.FeeValueVo;

import java.util.List;

/**
* @author cc
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Mapper
* @createDate 2024-07-24 15:48:00
* @Entity com.cc.lease.model.FeeValue
*/
public interface FeeValueMapper extends BaseMapper<FeeValue> {

    List<FeeValueVo> selectByApartmentId(Long id);
}




