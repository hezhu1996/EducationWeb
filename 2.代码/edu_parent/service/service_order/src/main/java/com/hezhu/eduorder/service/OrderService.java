package com.hezhu.eduorder.service;

import com.hezhu.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
public interface OrderService extends IService<Order> {

    //1.生成订单方法
    String createOrders(String courseId, String memberIdByJwtToken);
}
