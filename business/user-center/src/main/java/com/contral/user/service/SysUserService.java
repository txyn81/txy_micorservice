package com.contral.user.service;


import com.contral.common.core.model.SysUser;
import com.contral.common.core.service.SuperService;

public interface SysUserService extends SuperService<SysUser> {
    SysUser getUserByUserName(String name);
}
