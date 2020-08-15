package com.contral.user.service.impl;

import com.contral.common.core.model.Permission;
import com.contral.user.mapper.PermissionMapper;
import com.contral.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<String> findPermissionByUserId(Integer userId) {
        List<Permission> permissions = permissionMapper.findPermissionsByUserId(userId);
        List<String> permissionArr = new ArrayList<>();
        permissions.forEach(i -> permissionArr.add(i.getCode()));
        return permissionArr;
    }
}
