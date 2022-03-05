package com.mrpeng.controller;

import com.mrpeng.pojo.R;
import com.mrpeng.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("message")
public class MsgController {
    @Autowired
    private MsgService msgService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("getCode/{phone}")
    public R getCode(@PathVariable("phone")String phone){
        if(redisTemplate.opsForValue().get(phone)!=null){
             return R.ok().data("code",redisTemplate.opsForValue().get(phone));
        }
        String code=(int)((Math.random()*9+1)*100000)+"";
        Boolean msg = msgService.sendMsg(phone, code);
        if(msg){
            redisTemplate.opsForValue().set(phone,code,60, TimeUnit.MINUTES);
            return R.ok().message("发送验证码成功！");
        }else{
            return R.ok().message("验证码发送失败！");
        }
    }
}
