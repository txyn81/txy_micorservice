package com.contral.gateway.properties;

import java.util.ArrayList;
import java.util.List;

public class PermitProperties {
    /**
     * 认证中心
     */
    private static final String[] ENDPOINTS = {
            "/api-uaa/**",
    };


    /**
     * 设置不用认证的url
     */
    private String[] httpUrls = {};

    public String[] getUrls() {
        if (httpUrls == null || httpUrls.length == 0) {
            return ENDPOINTS;
        }
        List<String> list = new ArrayList<>();
        for (String url : ENDPOINTS) {
            list.add(url);
        }
        for (String url : httpUrls) {
            list.add(url);
        }
        return list.toArray(new String[list.size()]);
    }
}
