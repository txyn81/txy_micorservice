package com.contral.uaa.service.impl;

import com.contral.uaa.mapper.PermissionMapper;
import com.contral.uaa.model.Permission;
import com.contral.uaa.service.PermissionService;
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
