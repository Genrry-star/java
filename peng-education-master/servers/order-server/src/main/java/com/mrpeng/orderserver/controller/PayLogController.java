package com.mrpeng.orderserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mrpeng.orderserver.feign.CourseFeign;
import com.mrpeng.orderserver.service.OrderService;
import com.mrpeng.orderserver.service.PayLogService;
import com.mrpeng.pojo.EduCourse;
import com.mrpeng.pojo.Order;
import com.mrpeng.pojo.PayLog;
import com.mrpeng.pojo.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-25
 */
@RestController
@RequestMapping("/pay/weixinPay")
public class PayLogController {
    @Autowired
    private PayLogService PayService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CourseFeign courseFeign;
    @ApiOperation("获取支付码")
    @GetMapping("getPayCode/{orderNo}")
    public R getPayCode(@PathVariable("orderNo")String orderId){
        Map<String, String> map = PayService.PayCode(orderId);
        return  R.ok().data("result",map);

    }

    @ApiOperation("查询订单状态，并修改订单状态")
    @GetMapping("getOrderStatus/{orderNo}")
    public R getOrderStatus(@PathVariable String orderNo){
        Map<String, String> orderStatus = PayService.getOrderStatus(orderNo);
        if(orderStatus.get("trade_state").equals("SUCCESS")){
            PayService.updateAndInsert(orderStatus);
            QueryWrapper<Order> wrapper =new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order one = orderService.getOne(wrapper);
            //增加购买数量
            courseFeign.addBuyCount(one.getCourseId());
            return R.ok();
        }
        return R.ok().code(25000).message("正在支付中");
    }
}

