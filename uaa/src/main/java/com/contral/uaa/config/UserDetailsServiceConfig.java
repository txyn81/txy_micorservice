package com.contral.uaa.config;

import com.alibaba.fastjson.JSON;
import com.contral.common.core.feign.UserService;
import com.contral.common.core.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceConfig implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //将来链接数据库查询用户信息
        SysUser sysUser = userService.getUserByUserName(userName);
        if (sysUser == null) {
            //如果查询不到，就返回null，由security的provider来抛异常
            return null;
        }

        //查询用户的权限
        List<String> permissionsList = userService.findPermissionByUserId(sysUser.getId());
        //登陆账号
        System.out.println("userName:"+userName);
        //将UserDto转成json
        String principal = JSON.toJSONString(sysUser);
        UserDetails userDetails = User.withUsername(principal).password(sysUser.getPassword()).authorities(permissionsList.toArray(new String[0])).build();
        return userDetails;
    }
}
