package com.mrpeng.orderserver.service.impl;

import com.mrpeng.dto.CourseDetailsDto;
import com.mrpeng.dto.MemberDto;
import com.mrpeng.orderserver.feign.CourseFeign;
import com.mrpeng.orderserver.feign.MemberFeign;
import com.mrpeng.orderserver.utils.OrderNoUtils;
import com.mrpeng.pojo.Order;
import com.mrpeng.orderserver.mapper.OrderMapper;
import com.mrpeng.orderserver.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseFeign courseFeign;
    @Autowired
    private MemberFeign memberFeign;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public String saveOrder(String courseId,String userId) {
        //获取订单课程信息
        CourseDetailsDto course = courseFeign.getCourse(courseId);
        //获取会员信息
        MemberDto member = memberFeign.getMemberById(userId);

        Order order =new Order();
        order.setCourseId(course.getId());
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        order.setTeacherName(course.getTeacherName());
        order.setMemberId(member.getId());
        order.setNickname(member.getNickname());
        order.setMobile(member.getMobile());
        order.setTotalFee(course.getPrice());
        order.setPayType(1);
        order.setStatus(0);
        //生成订单号
        String orderNo = OrderNoUtils.getOrderNo();
        order.setOrderNo(orderNo);
        orderMapper.insert(order);
        return order.getOrderNo();

    }

}
