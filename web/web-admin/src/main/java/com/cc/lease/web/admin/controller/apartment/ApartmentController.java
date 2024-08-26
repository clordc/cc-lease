package com.cc.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.lease.common.result.Result;
import com.cc.lease.model.entity.ApartmentInfo;
import com.cc.lease.model.enums.ReleaseStatus;
import com.cc.lease.web.admin.service.ApartmentInfoService;
import com.cc.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.cc.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.cc.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.cc.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "公寓信息管理")
@RestController
@RequestMapping("/admin/apartment")
public class ApartmentController {

    @Autowired
    ApartmentInfoService service;

    @Operation(summary = "保存或更新公寓信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ApartmentSubmitVo apartmentSubmitVo) {
        service.saveOrUpdateApartment(apartmentSubmitVo);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询公寓列表")
    @GetMapping("pageItem")
    public Result<IPage<ApartmentItemVo>> pageItem(@RequestParam long current, @RequestParam long size, ApartmentQueryVo queryVo) {
        IPage<ApartmentItemVo> page = new Page<>(current,size);
        IPage<ApartmentItemVo> list = service.pageApartmentItemByQuery(page,queryVo);
        return Result.ok(list);
    }

    @Operation(summary = "根据ID获取公寓详细信息")
    @GetMapping("getDetailById")
    public Result<ApartmentDetailVo> getDetailById(@RequestParam Long id) {
        ApartmentDetailVo apartmentDetailVo = service.getApartmentDetailById(id);
        return Result.ok(apartmentDetailVo);
    }

    @Operation(summary = "根据id删除公寓信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        service.removeApartmentById(id);
        return Result.ok();
    }

    @Operation(summary = "根据id修改公寓发布状态")
    @PostMapping("updateReleaseStatusById")
    public Result updateReleaseStatusById(@RequestParam Long id, @RequestParam ReleaseStatus status) {
        LambdaUpdateWrapper<ApartmentInfo> apartmentUpdateWrapper = new LambdaUpdateWrapper<>();
        apartmentUpdateWrapper.eq(ApartmentInfo::getId,id);
        apartmentUpdateWrapper.set(ApartmentInfo::getIsRelease,status);
        service.update(apartmentUpdateWrapper);
        return Result.ok();
    }

    @Operation(summary = "根据区县id查询公寓信息列表")
    @GetMapping("listInfoByDistrictId")
    public Result<List<ApartmentInfo>> listInfoByDistrictId(@RequestParam Long id) {
        LambdaQueryWrapper<ApartmentInfo> apartmentQueryWrapper = new LambdaQueryWrapper<>();
        apartmentQueryWrapper.eq(ApartmentInfo::getDistrictId,id);
        List<ApartmentInfo> list = service.list(apartmentQueryWrapper);
        return Result.ok(list);
    }
}














