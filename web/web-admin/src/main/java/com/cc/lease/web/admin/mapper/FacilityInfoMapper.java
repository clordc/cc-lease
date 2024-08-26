package com.cc.lease.web.admin.mapper;

import com.cc.lease.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author cc
* @description 针对表【facility_info(配套信息表)】的数据库操作Mapper
* @createDate 2024-07-24 15:48:00
* @Entity com.cc.lease.model.FacilityInfo
*/
public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {

    List<FacilityInfo> selectByApartmentId(Long id);

    List<FacilityInfo> selectListByRoomId(Long id);
}




