package com.contral.file.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangxiyuan
 * @date 2020/7/31 5:55 下午
 * @description TODO
 */
@RestController
@Slf4j
public class FileController {

    @Value("${server.port}")
    private String port;

    @GetMapping("fileService")
    public String imageService() {
        return "file-center | "+ port;
    }
}
