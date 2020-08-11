package com.contral.uaa.service;

import java.util.List;

public interface PermissionService {

    List<String> findPermissionByUserId(Integer id);

}
