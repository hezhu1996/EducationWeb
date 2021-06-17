package com.hezhu.eduorder.service.impl;

import com.hezhu.commonutils.vo.CourseWebOrderVo;
import com.hezhu.commonutils.vo.UcenterMemberOrder;
import com.hezhu.eduorder.client.EduClient;
import com.hezhu.eduorder.client.UcenterClient;
import com.hezhu.eduorder.entity.Order;
import com.hezhu.eduorder.mapper.OrderMapper;
import com.hezhu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hezhu.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //1.生成订单方法
    @Override
    public String createOrders(String courseId, String memberId) {
        //1.远程调用：根据用户id获取用户全部信息(ucenter)
        System.out.println(memberId+"======================================");
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

        //2.远程调用：根据课程id获取全部课程信息(edu)
        CourseWebOrderVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建Order对象，向order对象中设置数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo()); //订单号：随机、唯一
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());

        order.setStatus(0);//支付状态：订单状态（0：未支付，1：已支付）
        order.setPayType(1);//支付类型：微信1

        //取出数据，并加入数据库
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
