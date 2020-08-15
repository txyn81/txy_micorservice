package com.contral.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.contral.common.core.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> findPermissionsByUserId(@Param("userId") Integer userId);
}
