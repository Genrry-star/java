package com.mrpeng.orderserver.service;

import com.mrpeng.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-23
 */
public interface OrderService extends IService<Order> {
    /**
     * 生成订单
     * @param courseId
     * @param userId
     * @return
     */
    String saveOrder(String courseId,String userId);

}
