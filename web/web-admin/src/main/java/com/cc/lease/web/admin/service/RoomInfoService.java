package com.cc.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.lease.model.entity.RoomInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.lease.web.admin.vo.room.RoomDetailVo;
import com.cc.lease.web.admin.vo.room.RoomItemVo;
import com.cc.lease.web.admin.vo.room.RoomQueryVo;
import com.cc.lease.web.admin.vo.room.RoomSubmitVo;

/**
* @author cc
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2024-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {

    void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo);

    IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);

    RoomDetailVo getRoomDetailById(Long id);

    void removeRoomById(Long id);
}
