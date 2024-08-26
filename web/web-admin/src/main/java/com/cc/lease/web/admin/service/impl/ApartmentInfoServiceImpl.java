package com.cc.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.lease.common.exception.LeaseException;
import com.cc.lease.model.entity.*;
import com.cc.lease.model.enums.ItemType;
import com.cc.lease.web.admin.mapper.*;
import com.cc.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.cc.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.cc.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.cc.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.cc.lease.web.admin.vo.fee.FeeValueVo;
import com.cc.lease.web.admin.vo.graph.GraphVo;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.cc.lease.common.result.ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR;

/**
 * @author cc
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2024-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private ApartmentFacilityService apartmentFacilityService;
    @Autowired
    private ApartmentLabelService apartmentLabelService;
    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;
    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
        boolean isUpdate = apartmentSubmitVo.getId()!=null;
        super.saveOrUpdate(apartmentSubmitVo);
        if(isUpdate){
            //1.删除图片列表
            LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId,apartmentSubmitVo.getId());
            graphInfoService.remove(graphInfoLambdaQueryWrapper);
            //2.删除配套列表
            LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,apartmentSubmitVo.getId());
            apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);
            //3.删除标签列表
            LambdaQueryWrapper<ApartmentLabel> apartmentLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,apartmentSubmitVo.getId());
            apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);
            //4.删除杂费列表
            LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apartmentFeeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId,apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(apartmentFeeValueLambdaQueryWrapper);
        }
        //1.插入图片列表
        List<GraphVo> graphVos = apartmentSubmitVo.getGraphVoList();
        if(!CollectionUtils.isEmpty(graphVos)){
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : graphVos) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }
        //2.插入配套列表
        List<Long> facilityInfoIdList = apartmentSubmitVo.getFacilityInfoIds();
        if(!CollectionUtils.isEmpty(facilityInfoIdList)){
            ArrayList<ApartmentFacility> apartmentFacilities = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIdList) {
                ApartmentFacility apartmentFacility = new ApartmentFacility();
                apartmentFacility.setApartmentId(apartmentSubmitVo.getId());
                apartmentFacility.setFacilityId(facilityInfoId);
                apartmentFacilities.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(apartmentFacilities);
        }
        //3.插入标签列表
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if(!CollectionUtils.isEmpty(labelIds)){
            ArrayList<ApartmentLabel> apartmentLabels = new ArrayList<>();
            for (Long labelId : labelIds) {
                ApartmentLabel apartmentLabel = new ApartmentLabel();
                apartmentLabel.setApartmentId(apartmentSubmitVo.getId());
                apartmentLabel.setLabelId(labelId);
                apartmentLabels.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabels);
        }
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if (!CollectionUtils.isEmpty(feeValueIds)) {
            ArrayList<ApartmentFeeValue> apartmentFeeValueList = new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                ApartmentFeeValue apartmentFeeValue = new ApartmentFeeValue();
                apartmentFeeValue.setApartmentId(apartmentSubmitVo.getId());
                apartmentFeeValue.setFeeValueId(feeValueId);
                apartmentFeeValueList.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValueList);
        }
    }

    @Override
    public IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo) {
        return apartmentInfoMapper.pageItem(page,queryVo);
    }

    @Override
    public ApartmentDetailVo getApartmentDetailById(Long id) {
        //1.查询公寓基本信息
        ApartmentInfo apartmentInfo = this.getById(id);
        if(apartmentInfo==null){
            return null;
        }
        //2.查询GraphVoList
        List<GraphVo> graphVos = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT,id);
        //3.查询LabelInfoList
        List<LabelInfo> labelInfos = labelInfoMapper.selectListByApartmentId(id);
        //4.查询FacilitiesInfoList
        List<FacilityInfo> facilityInfos = facilityInfoMapper.selectByApartmentId(id);
        //5.查询FeeValueList
        List<FeeValueVo> feeValueVos = feeValueMapper.selectByApartmentId(id);
        //6.合并数据
        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();
        BeanUtils.copyProperties(apartmentInfo,apartmentDetailVo);
        apartmentDetailVo.setGraphVoList(graphVos);
        apartmentDetailVo.setLabelInfoList(labelInfos);
        apartmentDetailVo.setFacilityInfoList(facilityInfos);
        apartmentDetailVo.setFeeValueVoList(feeValueVos);
        return apartmentDetailVo;
    }

    @Override
    public void removeApartmentById(Long id) {
        LambdaQueryWrapper<RoomInfo> roomQueryWrapper = new LambdaQueryWrapper<>();
        roomQueryWrapper.eq(RoomInfo::getApartmentId,id);
        long count = roomInfoMapper.selectCount(roomQueryWrapper);

        if(count>0){
            throw new LeaseException(ADMIN_APARTMENT_DELETE_ERROR);
        }

        this.removeById(id);
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId,id);
        graphInfoService.remove(graphInfoLambdaQueryWrapper);
        //2.删除配套列表
        LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,id);
        apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);
        //3.删除标签列表
        LambdaQueryWrapper<ApartmentLabel> apartmentLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,id);
        apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);
        //4.删除杂费列表
        LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apartmentFeeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId,id);
        apartmentFeeValueService.remove(apartmentFeeValueLambdaQueryWrapper);
    }
}




