package com.cc.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.lease.model.entity.RoomInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.lease.web.admin.vo.room.RoomItemVo;
import com.cc.lease.web.admin.vo.room.RoomQueryVo;

/**
* @author cc
* @description 针对表【room_info(房间信息表)】的数据库操作Mapper
* @createDate 2024-07-24 15:48:00
* @Entity com.cc.lease.model.RoomInfo
*/
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);
}




