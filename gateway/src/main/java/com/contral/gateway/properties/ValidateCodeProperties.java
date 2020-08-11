package com.contral.gateway.properties;

import lombok.Data;

@Data
public class ValidateCodeProperties {

    /**
     * 设置认证通时不需要验证码的clientId
     */
    private String[] ignoreClientCode = {};
}
