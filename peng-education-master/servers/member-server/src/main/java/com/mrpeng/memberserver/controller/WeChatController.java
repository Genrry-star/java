package com.mrpeng.memberserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import com.mrpeng.exception.NormalException;
import com.mrpeng.memberserver.pojo.UcenterMember;
import com.mrpeng.memberserver.service.UcenterMemberService;
import com.mrpeng.pojo.R;
import com.mrpeng.utils.HttpClient;
import com.mrpeng.utils.HttpClientUtils;
import com.mrpeng.utils.JWTUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/ucenter/wx") //这个一定要和回调路径保持一致:http://guli.shop/api/ucenter/wx/callback
@Slf4j
public class WeChatController {
    @Value("${wx.open.appid}")
    private String appid;
    @Value("${wx.open.appsecret}")
    private String appsercet;
    @Value("${wx.open.redirecturi}")
    private String redirecturi;

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation("得到微信二维码")
    @GetMapping("getWeChatCode")
    public String getWeChatCode(){
        String codePath="https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
        String url = String.format(codePath, appid, redirecturi);
        return "redirect:"+url;
    }

    @ApiOperation("扫码确认成功后，调用该方法")
    @GetMapping("callback")
    public String callback(String code,String state){
        try {
            String url1="https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
            url1 = String.format(url1, appid, appsercet, code);
            //使用自定义的网络请求对象， HttpClient可以模仿浏览器请求
            String tokenStr = HttpClientUtils.get(url1, "utf-8");
            log.info("token:"+tokenStr);
            

            //将token字符串,转换成对象
            JSONObject token = JSONObject.parseObject(tokenStr);
            String accessToken = token.get("access_token").toString();
            String openid=token.get("openid").toString();

            //对token进行验证
            String url2="https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";
            url2 = String.format(url2,accessToken,openid);
            String authStr = HttpClientUtils.get(url2, "utf-8");
            if (authStr.indexOf("ok")<0){
                throw new NormalException("二维码失效，登录失败！");
            }
            //验证该用户是否注册
            QueryWrapper<UcenterMember> wrapper =new QueryWrapper<>();
            wrapper.eq("openid",openid);
            UcenterMember member = ucenterMemberService.getOne(wrapper);
            if(member==null){
                //获取用户信息
                String url3="https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
                url3 = String.format(url3,accessToken,openid);
                String userStr = HttpClientUtils.get(url3);
                JSONObject userInfo = JSONObject.parseObject(userStr);
                //封装用户对象
                member =new UcenterMember();
                member.setOpenid(userInfo.get("openid").toString());
                member.setSex(userInfo.getInteger("sex"));
                member.setNickname(userInfo.get("nickname").toString());
                member.setAvatar(userInfo.get("headimgurl").toString());
                member.setIsDisabled(false);
                ucenterMemberService.save(member);
            }
            //得到我们自己的token，上面的token是微信的token，我们自己无法解析
            String jwtToken = JWTUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+jwtToken;

        } catch (Exception e) {
            e.printStackTrace();
            throw new NormalException("登录失败！");
        }


    }


}
