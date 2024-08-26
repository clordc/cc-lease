package com.cc.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.lease.model.entity.*;
import com.cc.lease.web.admin.mapper.LeaseAgreementMapper;
import com.cc.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.cc.lease.web.admin.vo.agreement.AgreementVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cc
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2024-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {
    @Autowired
    private LeaseTermService leaseTermService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private RoomInfoService roomInfoService;

    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;

    @Override
    public IPage<AgreementVo> pageAgreement(Page<AgreementVo> page, AgreementQueryVo queryVo) {
        return leaseAgreementMapper.pageAgreement(page,queryVo);
    }

    @Override
    public AgreementVo getAgreementById(Long id) {
        LeaseAgreement leaseAgreement = this.getById(id);

        ApartmentInfo apartmentInfo = apartmentInfoService.getById(leaseAgreement.getApartmentId());

        RoomInfo roomInfo = roomInfoService.getById(leaseAgreement.getRoomId());

        PaymentType paymentType = paymentTypeService.getById(leaseAgreement.getPaymentTypeId());

        LeaseTerm leaseTerm = leaseTermService.getById(leaseAgreement.getLeaseTermId());

        AgreementVo agreementVo = new AgreementVo();

        BeanUtils.copyProperties(leaseAgreement,agreementVo);

        agreementVo.setApartmentInfo(apartmentInfo);

        agreementVo.setLeaseTerm(leaseTerm);

        agreementVo.setPaymentType(paymentType);

        agreementVo.setRoomInfo(roomInfo);

        return agreementVo;
    }
}




