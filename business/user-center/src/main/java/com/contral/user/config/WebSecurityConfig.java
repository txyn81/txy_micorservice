package com.contral.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @author tangxiyuan
     * @date 2020/8/5 11:56 下午
     * @param http 1
     * @return
     * @description 不添加的话，就算控制器加了@PreAuthorize("hasAnyAuthority('p1')")权限也不会生效
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                //所有配置的路径都必须经过认证
                .antMatchers("/users-anon/**", "/permission-anon/**").permitAll()
                .antMatchers("/service/**").authenticated()

                .anyRequest().permitAll();

    }


}
