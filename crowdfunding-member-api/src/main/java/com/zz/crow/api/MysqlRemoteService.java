package com.zz.crow.api;

import com.zz.crow.po.MemberPO;
import com.zz.crow.response.ResultEntity;
import com.zz.crow.vo.ProjectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("zz-crow-mysql")
public interface MysqlRemoteService {

    @RequestMapping("get/memberpo/by/loginacct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginacctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("save/memberpo/remote")
    ResultEntity<String> saveMemberPORemote(@RequestBody MemberPO memberPO);

    @RequestMapping("save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);
}
