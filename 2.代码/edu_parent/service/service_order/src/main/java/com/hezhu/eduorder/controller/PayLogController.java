package com.hezhu.eduorder.controller;


import com.hezhu.commonutils.R;
import com.hezhu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //1.生成微信支付二维码接口
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        //1.返回信息，包含二维码地址，和其他信息
        Map map = payLogService.createNative(orderNo);
        System.out.println("==================返回二维码集合："+map);

        return R.ok().data(map);
    }

    //2.查询订单状态：根据订单号
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){

        //1.查询订单的所有信息（from微信）
        Map<String, String> map = payLogService.queryPayStatus(orderNo);

        //如果没有订单
        if (map == null) {
            return R.error().message("支付出错");
        }

        System.out.println("==================返回订单状态集合："+map);

        //如果订单map不为空，通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}




























