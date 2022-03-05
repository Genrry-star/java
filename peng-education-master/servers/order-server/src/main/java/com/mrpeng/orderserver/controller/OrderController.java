package com.mrpeng.orderserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrpeng.exception.NormalException;
import com.mrpeng.orderserver.service.OrderService;
import com.mrpeng.pojo.Order;
import com.mrpeng.pojo.R;
import com.mrpeng.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/orderserver/order")
@Api(tags = "订单的api")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @ApiOperation("保存订单")
    @PostMapping("saveOrder/{id}")
    public R saveOrder(@PathVariable("id")String courseId, HttpServletRequest request){
        if(!JWTUtils.checkToken(request)){
            return R.error().message("请先登录！");
        }
        String userId = JWTUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(userId)){
            return R.error().message("请先登录！");
        }
        String orderNo = orderService.saveOrder(courseId, userId);
        return R.ok().data("orderNo",orderNo);
    }

    @ApiOperation("根据id获取订单详情")
    @GetMapping("getOrderByNo/{orderNo}")
    public R getOrderByNo(@PathVariable("orderNo")String orderNo){
        QueryWrapper<Order> wrapper =new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order",order);
    }


    @ApiOperation("根据课程Id和用户Id，查询订单状态")
    @GetMapping("getOrderStatus/{courseId}")
    public R getOrderStatus(@PathVariable("courseId") String courseId,HttpServletRequest request){
        if(!JWTUtils.checkToken(request)){
            return R.ok().data("status",0);
        }
        String userId = JWTUtils.getMemberIdByJwtToken(request);
        QueryWrapper<Order> wrapper =new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",userId);
        Order order = orderService.getOne(wrapper);
        if(order==null){
            return R.ok().data("status",0);
        }
        return R.ok().data("status",order.getStatus());
    }
}

