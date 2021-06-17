package com.hezhu.mpdemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    //createTime
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //updateTime
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //乐观锁
    @Version
    @TableField(fill = FieldFill.INSERT) //自动添加version=1
    private int version;

    //逻辑删除
    @TableLogic
    private Integer deleted;
}
