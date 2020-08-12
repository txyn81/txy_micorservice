package com.contral.user.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.contral.core.model.SysUser;
import com.contral.core.utils.EncryptUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 将令牌信息解析出来
 * @author: oren.tang
 * @date: 2020/8/8 12:04 上午
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader("json-token");
        if (header != null) {
            //1. 解析出头当中的token
            String json = EncryptUtil.decodeUtf8StringBase64(header);
            //转为json对象
            JSONObject jsonObject = JSON.parseObject(json);
            //拿身份权限信息
            SysUser userDto = JSON.parseObject(jsonObject.getString("principal"), SysUser.class);
            JSONArray authoritiesArray = jsonObject.getJSONArray("authorities");
            String[] strings = authoritiesArray.toArray(new String[authoritiesArray.size()]);
            //2. 新建并填充authentication
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDto, null, AuthorityUtils.createAuthorityList(strings));
            //设置details
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            //将authenticationToken填充security的安全上下文中
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //过滤器继续执行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
