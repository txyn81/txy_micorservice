package com.contral.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @authot tangxiyuan
 * @date 2020/8/2 9:26 下午
 * @description @EnableAuthorizationServer 表示这个服务为授权服务
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    /**
     * 由于在TokenConfig中配置了一内存方式生成令牌，所以TokenStore已在配置类中生效，这里拿来用
     */
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * @author tangxiyuan
     * @date 2020/8/6 1:39 上午
     * @param dataSource 1
     * @return
     * @description 把客户详情服务由内存模式干掉改为数据库的形式，所以需要配置ClientDetailsService，需要在yml配置文件配置数据源
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    /**
     * @param clients 1
     * @return
     * @author tangxiyuan
     * @date 2020/8/2 9:42 下午
     * @description 客户端配置在实际生产中会配置在数据库中,将上面clientDetailsService注入后取出
     * 将客户端的信息存储到数据库
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * @return AuthorizationServerTokenServices
     * @author tangxiyuan
     * @date 2020/8/2 11:31 下午
     * @description 令牌管理服务，必须要，不管什么模式
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        //客户端信息服务
        services.setClientDetailsService(clientDetailsService);
        //是否产生刷新令牌
        services.setSupportRefreshToken(true);
        //令牌的存储策略
        services.setTokenStore(tokenStore);

        //将Converter设置进令牌服务，令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        services.setTokenEnhancer(tokenEnhancerChain);

//        默认令牌有效期为2小时
        services.setAccessTokenValiditySeconds(7200);
//        刷新令牌默认有效期3天
        services.setRefreshTokenValiditySeconds(259200);
        return services;
    }

    /**
     * @author tangxiyuan
     * @date 2020/8/3 12:22 上午
     * @return
     * @description 设置授权码模式的授权码如何保存，由内存修改为jdbc方式
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * @author tangxiyuan
     * @date 2020/8/2 11:32 下午
     * @param endpoints 1
     * @return
     * @description 令牌端点
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //密码模式,认证管理器
                .authenticationManager(authenticationManager)
                //授权码模式
                .authorizationCodeServices(authorizationCodeServices)
                //上面令牌管理服务
                .tokenServices(tokenServices())
                //允许POST提交
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * @author tangxiyuan
     * @date 2020/8/2 11:44 下午
     * @param security 1
     * @return
     * @description 令牌端点的安全策略
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //(1)
                .tokenKeyAccess("permitAll()")
                //(2)
                .checkTokenAccess("permitAll()")
                //(3)
                .allowFormAuthenticationForClients();
    }
}
