package com.cc.lease.web.admin.mapper;

import com.cc.lease.model.entity.AttrKey;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.lease.web.admin.vo.attr.AttrKeyVo;

import java.util.List;

/**
* @author cc
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Mapper
* @createDate 2024-07-24 15:48:00
* @Entity com.cc.lease.model.AttrKey
*/
public interface AttrKeyMapper extends BaseMapper<AttrKey> {

    List<AttrKeyVo> listAttrInfo();
}




