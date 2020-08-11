package com.contral.user.controller;

import com.contral.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: TODO
 * @author: oren.tang
 * @date: 2020/8/11 11:46 下午
 */
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/permission-anon/login")
    public List<String> findPermissionByUserId(@RequestParam("id") Integer id) {
        return permissionService.findPermissionByUserId(id);
    }
}
