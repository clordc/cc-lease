package com.cc.lease.web.admin.service.impl;

import com.cc.lease.model.entity.AttrKey;
import com.cc.lease.web.admin.mapper.AttrKeyMapper;
import com.cc.lease.web.admin.service.AttrKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.lease.web.admin.vo.attr.AttrKeyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author cc
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Service实现
* @createDate 2024-07-24 15:48:00
*/
@Service
public class AttrKeyServiceImpl extends ServiceImpl<AttrKeyMapper, AttrKey>
    implements AttrKeyService{

    @Autowired
    AttrKeyMapper attrKeyMapper;
    @Override
    public List<AttrKeyVo> listAttrInfo() {
        return attrKeyMapper.listAttrInfo();
    }
}




