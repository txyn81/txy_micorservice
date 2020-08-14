package com.contral.gateway.route.nacos;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.contral.gateway.config.ZuulRouteEntity;
import com.contral.gateway.constant.ZuulConstant;
import com.contral.gateway.route.AbstractDynRouteLocator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @description: nacos动态路由实现类
 * 在nacos对应的配置中，保存如下配置
 * [
 *     {
 *         "id" : "uaa",
 *         "path" : "/api-uaa/**",
 *         "serviceId" : "uaa-server",
 *         "stripPrefix" : true,
 *         "sensitiveHeaders" : "*",
 *         "enabled" : true,
 *         "customSensitiveHeaders" : true
 *     },{
 *         "id" : "user",
 *         "path" : "/api-user/**",
 *         "serviceId" : "user-server",
 *         "stripPrefix" : true,
 *         "sensitiveHeaders" : "*",
 *         "enabled" : true,
 *         "customSensitiveHeaders" : true
 *     },{
 *         "id" : "file",
 *         "path" : "/api-file/**",
 *         "serviceId" : "file-server",
 *         "stripPrefix" : true,
 *         "sensitiveHeaders" : "*",
 *         "enabled" : true,
 *         "customSensitiveHeaders" : true
 *     }
 *
 * ]
 * @author: oren.tang
 * @date: 2020/8/14 8:27 下午
 */
@Slf4j
public class NacosDynRouteLocator extends AbstractDynRouteLocator {

    private NacosConfigProperties nacosConfigProperties;

    private ApplicationEventPublisher publisher;

    private NacosDynRouteLocator locator;

    @Setter
    private List<ZuulRouteEntity> zuulRouteEntities;

    public NacosDynRouteLocator(NacosConfigProperties nacosConfigProperties, ApplicationEventPublisher publisher,
                                String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.nacosConfigProperties = nacosConfigProperties;
        this.publisher = publisher;
        this.locator = this;
        addListenter();
    }

    private void addListenter() {
        try {
            nacosConfigProperties.configServiceInstance().addListener(ZuulConstant.ZUUL_DATA_ID, ZuulConstant.ZUUL_GROUP_ID, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    //赋值路由信息
                    locator.setZuulRouteEntities(getListByStr(configInfo));
                    RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(locator);
                    publisher.publishEvent(routesRefreshedEvent);
                }
            });
        } catch (NacosException e) {
            log.error("nacos-addListener-error", e);
        }
    }

    @Override
    public Map<String, ZuulRoute> loadDynamicRoutr() {
        Map<String, ZuulRoute> routes = new LinkedHashMap<>();
        if (zuulRouteEntities == null) {
            zuulRouteEntities = getNacosConfig();
        }
        for (ZuulRouteEntity result : zuulRouteEntities) {
            if (StrUtil.isBlank(result.getPath()) || !result.isEnabled()) {
                continue;
            }
            ZuulRoute zuulRoute = new ZuulRoute();
            BeanUtil.copyProperties(result, zuulRoute);
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }

    /**
     * 查询zuul的路由配置
     * @return
     */
    private List<ZuulRouteEntity> getNacosConfig() {
        try {
            String content = nacosConfigProperties.configServiceInstance().getConfig(ZuulConstant.ZUUL_DATA_ID, ZuulConstant.ZUUL_GROUP_ID, 5000);
            return getListByStr(content);
        } catch (NacosException e) {
            log.error("listenterNacos-error", e);
        }
        return new ArrayList<>(0);
    }

    public List<ZuulRouteEntity> getListByStr(String content) {
        if (StrUtil.isNotEmpty(content)) {
            return JSONObject.parseArray(content, ZuulRouteEntity.class);
        }
        return new ArrayList<>(0);
    }
}
