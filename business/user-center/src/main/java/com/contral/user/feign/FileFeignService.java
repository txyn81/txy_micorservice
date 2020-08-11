package com.contral.user.feign;

import com.contral.user.feign.fallback.FileFengnServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "file-server", fallback = FileFengnServiceFallBack.class)
public interface FileFeignService {

    @GetMapping("fileService")
    String fileService();
}
