package com.mrpeng.memberserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.j2objc.annotations.AutoreleasePool;
import com.mrpeng.exception.NormalException;
import com.mrpeng.memberserver.mapper.UcenterMemberMapper;
import com.mrpeng.memberserver.pojo.MemberVo;
import com.mrpeng.memberserver.pojo.RegisterVo;
import com.mrpeng.memberserver.pojo.UcenterMember;
import com.mrpeng.memberserver.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrpeng.utils.JWTUtils;
import com.mrpeng.utils.MD5;
import javafx.beans.binding.BooleanBinding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-21
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private UcenterMemberMapper memberMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public void register(RegisterVo info) {
        System.out.println(info);
        if(!isValidated(info)){
            throw new NormalException("注册失败！");
        }
        QueryWrapper<UcenterMember> wrapper =new QueryWrapper<>();
        wrapper.eq("mobile",info.getMobile());
        UcenterMember member = memberMapper.selectOne(wrapper);
        if(member!=null){
            throw new NormalException("该手机号已经被注册！");
        }
        UcenterMember ucenterMember =new UcenterMember();
        BeanUtils.copyProperties(info,ucenterMember);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603277998577&di=479be70a08f86dc07f13f6482d6bac50&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201603%2F06%2F20160306204517_i4Se8.jpeg");
        String encrypt = MD5.encrypt(info.getPassword());
        ucenterMember.setPassword(encrypt);
        memberMapper.insert(ucenterMember);
    }

    @Override
    public String login(MemberVo member) {
        String mobile =member.getMobile();
        String password=member.getPassword();
        System.out.println(member);
        //查询数据库的信息
        QueryWrapper<UcenterMember> wrapper =new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = memberMapper.selectOne(wrapper);
        //验证登录信息是否正确
        if(!isValidated(mobile,password,ucenterMember)) throw new NormalException("登陆失败");
        String token = JWTUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getPassword());
        return token;
    }
    //检验登录信息是否正确
    private Boolean isValidated(String mobile,String password,UcenterMember member){
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            return false;
        }
        if(member==null){
            return false;
        }
        if(!member.getPassword().equals(MD5.encrypt(password))){
            return false;
        }
        return true;
    }

    //查看注册数据数据是否合格
    private Boolean isValidated(RegisterVo info){
        if(StringUtils.isEmpty(info.getNickname())||StringUtils.isEmpty(info.getMobile())||
                StringUtils.isEmpty(info.getCode())||StringUtils.isEmpty(info.getPassword())){
            return false;
        }
        if(!redisTemplate.opsForValue().get(info.getMobile()).equals(info.getCode())){
            return false;
        }
        return true;
    }
}
