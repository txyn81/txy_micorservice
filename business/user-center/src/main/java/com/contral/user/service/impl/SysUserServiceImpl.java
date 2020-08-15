package com.contral.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.contral.common.core.model.SysUser;
import com.contral.common.core.service.impl.SuperServiceImpl;
import com.contral.user.mapper.UserServiceMapper;
import com.contral.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends SuperServiceImpl<UserServiceMapper, SysUser> implements SysUserService {
    @Autowired
    private UserServiceMapper userServiceMapper;
    @Override
    public SysUser getUserByUserName(String name) {
        LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(SysUser::getUsername, name);
        return userServiceMapper.selectOne(userQueryWrapper);
    }
}
