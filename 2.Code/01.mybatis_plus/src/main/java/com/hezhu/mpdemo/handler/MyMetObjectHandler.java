package com.hezhu.mpdemo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

//交给spring管理
@Component
public class MyMetObjectHandler implements MetaObjectHandler {
    //mp添加操作，这个方法执行
    @Override
    public void insertFill(MetaObject metaObject) {
        //(属性，要设置的值，元数据[数据的数据])
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("version", 1, metaObject);
    }

    //mp修改操作，这个方法执行
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
