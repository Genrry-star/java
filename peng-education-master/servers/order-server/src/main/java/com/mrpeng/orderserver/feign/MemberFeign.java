package com.mrpeng.orderserver.feign;

import com.mrpeng.dto.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("member-server")
public interface MemberFeign {
    @GetMapping("/memberserver/ucenter-member/getMemberById/{id}")
    MemberDto getMemberById(@PathVariable("id")String userId);

}
