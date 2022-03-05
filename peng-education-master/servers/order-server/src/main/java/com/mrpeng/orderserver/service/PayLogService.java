package com.mrpeng.orderserver.service;

import com.mrpeng.pojo.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-25
 */
public interface PayLogService extends IService<PayLog> {
    /**
     * 生成订单二维码
     * @param orderNo 订单id
     */
    Map<String,String> PayCode(String orderNo);

    Map<String,String> getOrderStatus(String orderNo);

    void updateAndInsert(Map<String,String> contentMap);

    Map getStatisticsData(String date);


}
