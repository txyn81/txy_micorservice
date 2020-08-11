package com.contral.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.contral.uaa.mapper.UserServiceMapper;
import com.contral.uaa.model.SysUser;
import com.contral.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserServiceMapper userServiceMapper;
    @Override
    public SysUser getUserByUserName(String name) {
        LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(SysUser::getUsername, name);
        return userServiceMapper.selectOne(userQueryWrapper);
    }
}
