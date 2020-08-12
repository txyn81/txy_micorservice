package com.contral.user.service;


import com.contral.core.model.SysUser;

public interface SysUserService {
    SysUser getUserByUserName(String name);
}
