package com.contral.core.feign.impl;

import com.contral.core.feign.UserService;
import com.contral.core.model.SysUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: oren.tang
 * @date: 2020/8/11 11:39 下午
 */
@Slf4j
@Component
public class UserServiceFallBackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {

            @Override
            public SysUser getUserByUserName(String userName) {
                log.error("通过用户名查询用户异常{}", userName, throwable);
                return new SysUser();
            }

            @Override
            public List<String> findPermissionByUserId(Integer id) {
                log.error("通过用户名id查询用户权限异常{}", id, throwable);
                return new ArrayList<>();
            }
        };
    }
}
