package com.cc.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.lease.model.entity.ViewAppointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.cc.lease.web.admin.vo.appointment.AppointmentVo;

/**
* @author cc
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Mapper
* @createDate 2024-07-24 15:48:00
* @Entity com.cc.lease.model.ViewAppointment
*/
public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {

    IPage<AppointmentVo> pageAppointmentByQuery(IPage<AppointmentVo> page, AppointmentQueryVo queryVo);
}




