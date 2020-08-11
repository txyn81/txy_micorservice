package com.contral.core.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("t_user_role")
public class UserRole {

  private long userId;
  private long roleId;
  private java.sql.Timestamp createTime;
  private String creator;
}
