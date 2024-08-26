package com.cc.lease.web.admin.mapper;

import com.cc.lease.model.entity.LabelInfo;
import com.cc.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author cc
* @description 针对表【label_info(标签信息表)】的数据库操作Mapper
* @createDate 2024-07-24 15:48:00
* @Entity com.cc.lease.model.LabelInfo
*/
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    List<LabelInfo> selectListByApartmentId(Long id);

    List<LabelInfo> selectListByRoomId(Long id);
}




