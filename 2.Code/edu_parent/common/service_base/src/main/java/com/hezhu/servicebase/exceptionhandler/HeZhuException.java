package com.hezhu.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //有参构造
@NoArgsConstructor //无参构造
public class HeZhuException extends RuntimeException{
    @ApiModelProperty(value = "状态码")
    private Integer code; //状态码
    private String msg; //信息

    @Override
    public String toString() {
        return "HeZhuException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }
}
