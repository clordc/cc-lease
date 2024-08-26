package com.cc.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.lease.model.entity.ApartmentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.cc.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.cc.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.cc.lease.web.admin.vo.apartment.ApartmentSubmitVo;

/**
* @author cc
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2024-07-24 15:48:00
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {

    void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo);

    IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo);

    ApartmentDetailVo getApartmentDetailById(Long id);

    void removeApartmentById(Long id);
}
