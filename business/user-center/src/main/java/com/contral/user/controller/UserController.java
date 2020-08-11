package com.contral.user.controller;

import com.contral.user.feign.FileFeignService;
import com.contral.user.model.SysUser;
import com.contral.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private FileFeignService fileFeignService;

    @Resource
    private SysUserService sysUserService;

    @Value("${server.port}")
    private String port;

    /**
     * @author tangxiyuan
     * @date 2020/8/5 10:08 下午
     * @return
     * @description 授权控制，拥有p1权限才可以访问
     */
    @GetMapping("service/s1")
    @PreAuthorize("hasAnyAuthority('p1')")
    public String service() {
        SysUser userDto = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDto + "user-center | "+ port + "";
    }

    @GetMapping("service/feign")
    public String serviceFeign() {
        String s = fileFeignService.fileService();
        return "user-center | "+ port + s;
    }

    @GetMapping("/users-anon/login")
    public SysUser getUserByUserName(@RequestParam("userName") String userName) {
        return sysUserService.getUserByUserName(userName);
    }
}
