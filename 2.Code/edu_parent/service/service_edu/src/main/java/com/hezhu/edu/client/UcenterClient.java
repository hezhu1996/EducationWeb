package com.hezhu.edu.client;

import com.hezhu.commonutils.vo.UcenterMemberVo;
import com.hezhu.edu.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {
    //4.根据用户id查询用户信息
    @PostMapping("/educenter/member/getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable String memberId);
}
