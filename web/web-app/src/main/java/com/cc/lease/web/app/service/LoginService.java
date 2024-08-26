package com.cc.lease.web.app.service;

import com.cc.lease.web.app.vo.user.LoginVo;
import com.cc.lease.web.app.vo.user.UserInfoVo;

public interface LoginService {
    void sendcode(String phone);

    String login(LoginVo loginVo);

    UserInfoVo getUserInfoById(Long userId);
}
