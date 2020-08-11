package com.contral.uaa.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("t_user")
public class SysUser {

  private long id;
  private String username;
  private String password;
  private String fullname;
  private String mobile;
}
