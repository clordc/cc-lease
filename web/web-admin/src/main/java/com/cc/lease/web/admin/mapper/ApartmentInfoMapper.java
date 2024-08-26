package com.cc.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.lease.model.entity.ApartmentInfo;
import com.cc.lease.model.enums.LeaseStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.cc.lease.web.admin.vo.apartment.ApartmentQueryVo;
import org.springframework.context.annotation.Bean;

/**
* @author cc
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2024-07-24 15:48:00
* @Entity com.cc.lease.model.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    IPage<ApartmentItemVo> pageItem(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo);
}




