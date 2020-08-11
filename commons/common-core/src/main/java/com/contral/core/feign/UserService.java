package com.contral.core.feign;

import com.contral.core.constant.ServiceNameConstants;
import com.contral.core.feign.impl.UserServiceFallBackFactory;
import com.contral.core.model.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: TODO
 * @author: oren.tang
 * @date: 2020/8/11 11:33 下午
 */
@FeignClient(name = ServiceNameConstants.USER_SERVER, fallbackFactory = UserServiceFallBackFactory.class, decode404 = true)
public interface UserService {

    @GetMapping("/users-anon/login")
    SysUser getUserByUserName(@RequestParam("userName") String userName);

    @GetMapping("/permission-anon/login")
    List<String> findPermissionByUserId(@RequestParam("id") Integer id);
}
