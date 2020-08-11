package com.contral.user.service;

import com.contral.user.model.SysUser;

public interface SysUserService {
    SysUser getUserByUserName(String name);
}
