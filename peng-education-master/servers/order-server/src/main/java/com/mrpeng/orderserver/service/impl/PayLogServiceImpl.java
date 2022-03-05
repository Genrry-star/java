package com.mrpeng.orderserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.mrpeng.exception.IllegalParamException;
import com.mrpeng.exception.NormalException;
import com.mrpeng.pojo.EduCourse;
import com.mrpeng.pojo.PayLog;
import com.mrpeng.orderserver.mapper.PayLogMapper;
import com.mrpeng.orderserver.service.OrderService;
import com.mrpeng.orderserver.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrpeng.pojo.Order;
import com.mrpeng.utils.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-25
 */
@Service
@Slf4j
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Value("${weixin.pay.appid}")
    private String appid;  //公众账号的id
    @Value("${weixin.pay.partner}")
    private String partner;  //商家号
    @Value("${weixin.pay.partnerkey}")
    private String partnerkey;  //商家密钥
    @Value("${weixin.pay.notifyurl}")
    private String notifyurl;  //回调地址
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayLogMapper payLogMapper;
    @Override
    public Map<String, String> PayCode(String orderNo) {
        try {
            //获取订单
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);
            //生成二维码的网络地址https://api.mch.weixin.qq.com/pay/unifiedorder
            String url1 = "https://api.mch.weixin.qq.com/pay/unifiedorder";

            //将请求的参数放入map集合中
//            Map<String, String> params = new HashMap<>();
//            params.put("appid", "wx74862e0dfcf69954");
//            params.put("mch_id", partner);
//            params.put("nonce_str", WXPayUtil.generateNonceStr());
//            params.put("body", order.getCourseTitle());
//            params.put("out_trade_no", order.getOrderNo());
//            params.put("total_fee", order.getTotalFee());
//            params.put("spbill_create_ip", "127.0.0.1");
//            params.put("notify_url", notifyurl);
//            params.put("trade_type", "NATIVE");
            Map<String,String> m=new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号
            m.put("total_fee","1");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            m.put("trade_type", "NATIVE");
            HttpClient client = new HttpClient(url1);
            //partnerkey为商家密钥，可以直接放入参数集合中，在这里使用generateSignedXml方法和直接放入效果一样
            String xmlParams = WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb");
            log.info("生成的xml参数字符串："+xmlParams);
            client.setXmlParam(xmlParams);
            client.setHttps(true); //允许请求https请求
            client.post();  //请求

            String content = client.getContent();//获取请求结果  xml文件格式
            log.info("请求成功，返回的xml内容："+content);

            //将返回带xml格式文件转成map结合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);



            //需要返回的结果
            Map<String, String> map =new HashMap<>();
            map.put("orderNo",order.getOrderNo());
            map.put("courseId",order.getCourseId());
            map.put("return_code",resultMap.get("return_code"));
            map.put("return_msg",resultMap.get("return_msg"));
            map.put("total_fee",order.getTotalFee());
            map.put("code_url",resultMap.get("code_url"));


            return map;

        }catch (Exception ex){
            throw new NormalException("二维码生成失败！");
        }
    }

    @Override
    public Map<String,String> getOrderStatus(String orderNo) {
        try {
            String url = "https://api.mch.weixin.qq.com/pay/orderquery";
            //请求所需要的参数
            Map<String, String> map = new HashMap<>();
            map.put("appid", appid);
            map.put("mch_id", partner);
            map.put("out_trade_no", orderNo);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            //将参数结合转换程xml字符串
            String xmlParams = WXPayUtil.generateSignedXml(map, partnerkey);
            //查询订单状态
            HttpClient client =new HttpClient(url);
            client.setHttps(true);
            client.setXmlParam(xmlParams);
            client.post();
            //获取查询信息
            String content = client.getContent();
            Map<String, String> contentMap = WXPayUtil.xmlToMap(content);
            log.info("订单状态信息："+contentMap);

            if(contentMap==null){
                throw new NormalException("订单异常！");
            }
            return contentMap;
        }catch (Exception ex){
            throw new NormalException("获取订单状态异常");
        }
    }

    @Override
    public void updateAndInsert(Map<String, String> contentMap) {
        //修改订单状态
        UpdateWrapper<Order> wrapper =new UpdateWrapper<>();
        wrapper.set("status",1);
        wrapper.eq("order_no",contentMap.get("out_trade_no"));
        orderService.update(wrapper);
        //检查是否存在消费记录，避免重复消费
        QueryWrapper<PayLog> wrapper1 =new QueryWrapper<>();
        wrapper1.eq("order_no",contentMap.get("out_trade_no"));
        Integer count = payLogMapper.selectCount(wrapper1);
        if(count==0){
            //添加消费记录
            PayLog payLog =new PayLog();
            payLog.setOrderNo(contentMap.get("out_trade_no"));
            payLog.setPayTime(new Date());
            payLog.setTotalFee(new BigDecimal(contentMap.get("total_fee")).divide(new BigDecimal(100)));
            payLog.setTransactionId(contentMap.get("transaction_id"));
            payLog.setTradeState(contentMap.get("trade_state"));
            payLog.setPayType(1);
            payLogMapper.insert(payLog);


        }


    }

    @Override
    public Map getStatisticsData(String date) {
        return payLogMapper.findStatisticsData(date);
    }

    private void tradeStatus(String status){
        if(status.equals("NOTPAY")){
            throw new NormalException("订单未支付!",20001);
        }
        if(status.equals("CLOSED")){
            throw new NormalException("订单已关闭",20001);
        }
        if(status.equals("REVOKED")){
            throw new NormalException("订单已撤销！",20001);
        }
    }
}
