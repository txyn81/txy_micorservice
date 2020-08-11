package com.contral.user.feign.fallback;

import com.contral.user.feign.FileFeignService;
import org.springframework.stereotype.Component;

@Component
public class FileFengnServiceFallBack implements FileFeignService {


    @Override
    public String fileService() {
        return "fileService 降级";
    }
}
