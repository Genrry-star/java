package com.mrpeng.memberserver.controller;


import com.mrpeng.dto.MemberDto;
import com.mrpeng.memberserver.pojo.MemberVo;
import com.mrpeng.memberserver.pojo.RegisterVo;
import com.mrpeng.memberserver.pojo.UcenterMember;
import com.mrpeng.memberserver.service.UcenterMemberService;
import com.mrpeng.pojo.R;
import com.mrpeng.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-21
 */
@RestController
@RequestMapping("/memberserver/ucenter-member")
@Api(tags = "会员的api")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation("用户登录")
    @PostMapping("login")
    public R login(@RequestBody MemberVo member){
        String token = ucenterMemberService.login(member);
        return R.ok().data("token",token);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //判断token令牌是否有效
        if(!JWTUtils.checkToken(request)){
            return R.ok().error().message("用户没有登录");
        }
        //获取令牌中的用户名（手机号）
        String memberId = JWTUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("member",member);
    }

    @ApiOperation("注册用户")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo info){
        ucenterMemberService.register(info);
        return R.ok().message("注册成功！");
    }
    @ApiOperation("服务调用方法。获取登录用户的信息")
    @GetMapping("getMemberById/{id}")
    public MemberDto getMemberById(@PathVariable("id")String userId) {
        UcenterMember member = ucenterMemberService.getById(userId);
        MemberDto memberDto =new MemberDto();
        BeanUtils.copyProperties(member,memberDto);
        return memberDto;
    }


}

