package com.hezhu.servicebase.exceptionhandler;

import com.hezhu.commonutils.ExceptionUtil;
import com.hezhu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回出错数据json
    public R error(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().message("执行了自定义异常");
    }

    //自定义异常
    @ExceptionHandler(HeZhuException.class)
    @ResponseBody
    public R error(HeZhuException e){
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
