package com.contral.common.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @description: 编码转换工具类
 * @author: oren.tang
 * @date: 2020/8/7 11:42 下午
 */
@Slf4j
public class EncryptUtil {

    public static String encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] decodeBase64 (String str){
        return Base64.getDecoder().decode(str);
    }

    public static String encodeUtf8StringBase64(String str) {
        String encoded = null;
        try {
            encoded = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.warn("不支持的编码格式", e);
        }
        return encoded;
    }

    public static String decodeUtf8StringBase64 (String str) {
        String decoded = null;
        byte[] bytes = Base64.getDecoder().decode(str);
        decoded = new String(bytes, StandardCharsets.UTF_8);
        return decoded;
    }

    public static String encodeUrl (String url) {
        String encoded =null;
        try {
            encoded= URLEncoder.encode (url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.warn ("URLEncode失败", e);
        }
        return encoded;
    }

    public static String decodeUrl (String url) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode (url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.warn ("URLDecode失败", e);
        }
        return decoded;
    }
}
