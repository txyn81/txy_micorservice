package com.contral.user.service;


import com.contral.core.model.SysUser;
import com.contral.core.service.SuperService;

public interface SysUserService extends SuperService<SysUser> {
    SysUser getUserByUserName(String name);
}
