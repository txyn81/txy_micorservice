package com.contral.user.controller;

import com.contral.common.core.annotation.LoginUser;
import com.contral.common.core.model.SysUser;
import com.contral.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tangxiyuan
 * @date 2020/7/31 5:34 下午
 * @description user模拟测试类
 */
@RestController
@Slf4j
public class UserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * @author tangxiyuan
     * @date 2020/8/5 10:08 下午
     * @return
     * @description 授权控制，拥有p1权限才可以访问
     */
    @GetMapping("service/s1")
    @PreAuthorize("hasAnyAuthority('p1')")
    public SysUser service(@LoginUser(isFull = true) SysUser sysUser) {
        return sysUser;
    }

    @PostMapping("testParam")
    public SysUser testParam(@RequestBody SysUser sysUser,@RequestParam("tt") Integer tt) {
        System.out.println(tt);
        return sysUser;
    }

    @GetMapping("/users-anon/login")
    public SysUser getUserByUserName(@RequestParam("userName") String userName) {
        return sysUserService.getUserByUserName(userName);
    }
}
