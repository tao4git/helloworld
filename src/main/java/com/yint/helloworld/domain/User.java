package com.yint.helloworld.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private Long id;
    private String uname;
    private String upwd;
    private String createTime;
    private String roleId;
    private String status;
}
