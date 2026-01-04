package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Column(name = "real_name")  // 数据库字段是下划线
    private String realName;      // Java字段用驼峰

    private String phone;
    private Integer role;
    private Integer status = 1;
    private String gender;
    private String signature;
}