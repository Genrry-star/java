package com.mrpeng.memberserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrpeng.memberserver.pojo.MemberVo;
import com.mrpeng.memberserver.pojo.RegisterVo;
import com.mrpeng.memberserver.pojo.UcenterMember;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-21
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    /**
     * 注册用户
     * @param info  注册信息
     */
    void register(RegisterVo info);

    String login(MemberVo member);
}
