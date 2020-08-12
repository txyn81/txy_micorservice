package com.contral.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 资源的名称，和uaa配置的资源得一致
     */
    public static final String RESOURCE_ID = "res1";

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                //资源id
                .resourceId(RESOURCE_ID)
                //去除验证令牌的服务，加入JWT，服务自己来验证令牌
                .tokenStore(tokenStore)
                .stateless(true);
    }

    /**
     * @author tangxiyuan
     * @date 2020/8/5 10:21 下午
     * @param http 1
     * @return
     * @description 校验uaa中的授权范围是不是all
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/users-anon/**", "/permission-anon/**").permitAll()
                .antMatchers("/**").access("#oauth2.hasScope('ROLE_ADMIN')")
                .and()
                .csrf().disable()
                //不用记录session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
