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
@TableName("t_role")
public class Role {

  private long id;
  private String roleName;
  private String description;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;
  private String status;

}
