package com.contral.user.service;

import java.util.List;

public interface PermissionService {

    List<String> findPermissionByUserId(Integer id);

}
