package com.cc.lease.web.admin.service;

import com.cc.lease.web.admin.vo.login.CaptchaVo;
import com.cc.lease.web.admin.vo.login.LoginVo;
import com.cc.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {

    CaptchaVo getCaptcha();

    String login(LoginVo loginVo);

    SystemUserInfoVo getSystemUserInfoVo(Long userId);
}
