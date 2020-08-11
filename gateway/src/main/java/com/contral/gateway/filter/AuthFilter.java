package com.contral.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.contral.gateway.utils.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 测试像header中写值然后后续微服务取值
 * @author: oren.tang
 * @date: 2020/8/7 11:03 下午
 */
@Component
@Slf4j
public class AuthFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -200;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        //拿到security的身份对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof OAuth2Authentication)) {
            return null;
        }
        //将身份对象转为Oauth2格式
        OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
        //获取当前用户的身份信息
        Authentication userAuthentication = auth2Authentication.getUserAuthentication();
        //取出身份信息
        String principal = userAuthentication.getName();
        //获取当前用户的权限信息
        List<String> authorities = new ArrayList<>();
        userAuthentication.getAuthorities().forEach(p -> authorities.add(p.getAuthority()));
        //获取令牌中其他请求数据
        OAuth2Request oAuth2Request = auth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
        Map<String, Object> jsonToken = new HashMap<>(requestParameters);
        if (auth2Authentication != null) {
            jsonToken.put("principal", principal);
            jsonToken.put("authorities",authorities);
        }
        //把身份信息和权限信息放到json中，加入http header中
        /*Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            httpHeader.set("json-token", EncryptUtil.encodeUtf8StringBase64(JSON.toJSONString(jsonToken)));
        };
        //转发给微服务
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
        ServerWebExchange build = exchange.mutate().request(serverHttpRequest).build();*/

        ctx.addZuulRequestHeader("json-token",EncryptUtil.encodeUtf8StringBase64(JSON.toJSONString(jsonToken)));
        return null;
    }

}
