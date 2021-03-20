package com.hezhu.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hezhu.commonutils.JwtUtils;
import com.hezhu.commonutils.R;
import com.hezhu.eduorder.entity.Order;
import com.hezhu.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //1.生成订单方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("orderId", orderNo);
    }

    //2.根据订单号，查询订单全部信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId); //这里是根据order_no查询，而不是真正的id
        Order order = orderService.getOne(wrapper);

        return R.ok().data("item", order);
    }

    //3.根据 课程id 和 用户id：查询订单表中的订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId ){
        //memberId可以从request(token)中取值，更好
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1); //支付状态，1代表已经支付
        int count = orderService.count(wrapper);
        if(count > 0){
            //已支付
            return true;
        }
        else {
            return false;
        }
    }

}

