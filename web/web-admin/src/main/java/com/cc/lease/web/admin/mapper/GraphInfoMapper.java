package com.cc.lease.web.admin.mapper;

import com.cc.lease.model.entity.GraphInfo;
import com.cc.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.lease.web.admin.vo.graph.GraphVo;

import java.util.List;

/**
* @author cc
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2024-07-24 15:48:00
* @Entity com.cc.lease.model.GraphInfo
*/
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    List<GraphVo> selectListByItemTypeAndId(ItemType itemType, Long id);
}




