package com.hezhu.edu.client.impl;

import com.hezhu.edu.client.OrdersClient;
import org.springframework.stereotype.Component;

@Component
public class OrderClientImpl implements OrdersClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        System.out.println("===========================================");
        System.out.println("courseId: "+ courseId);

        System.out.println("===========================================");
        System.out.println("memberId: "+ memberId);
        return false;
    }
}
