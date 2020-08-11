package com.contral.uaa.service;


import com.contral.uaa.model.SysUser;

public interface UserService {
    SysUser getUserByUserName(String name);
}
