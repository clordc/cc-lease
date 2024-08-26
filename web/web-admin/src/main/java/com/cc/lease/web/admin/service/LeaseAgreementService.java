package com.cc.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.lease.model.entity.LeaseAgreement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.cc.lease.web.admin.vo.agreement.AgreementVo;

/**
* @author cc
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2024-07-24 15:48:00
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {

    IPage<AgreementVo> pageAgreement(Page<AgreementVo> page, AgreementQueryVo queryVo);

    AgreementVo getAgreementById(Long id);
}
