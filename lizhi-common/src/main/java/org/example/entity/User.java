package org.example.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")  // 指定表名
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String username;
    private String password;
    private String real_name;
    private String phone;
    private Integer role;
    private Integer status = 1;
    private String gender;
    private String signature;
}