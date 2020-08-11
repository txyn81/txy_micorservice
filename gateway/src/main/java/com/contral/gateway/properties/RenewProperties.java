package com.contral.gateway.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RenewProperties {

    /**
     * 是否打开token自动续签（目前待完成redis续签）
     */
    private Boolean enable = false;

    /**
     * 白名单，配置需要自动续签的应用id（与黑名单互斥，只能配置其中一个），不配置默认所有应用都生效
     * 配置enable为true的时候才生效
     * 待完成
     */
    private List<String> inCludeClientIds = new ArrayList<>();

    /**
     * 续签时间比例，当前剩余时间小于过期总时长的50%则续签
     */
    private Double timeRatio = 0.5;
}
