package com.cc.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.lease.model.entity.ViewAppointment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.cc.lease.web.admin.vo.appointment.AppointmentVo;

/**
* @author cc
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service
* @createDate 2024-07-24 15:48:00
*/
public interface ViewAppointmentService extends IService<ViewAppointment> {

    IPage<AppointmentVo> pageAppointmentByQuery(IPage<AppointmentVo> page, AppointmentQueryVo queryVo);
}
