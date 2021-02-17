package com.zz.crow.controller;

import com.zz.crow.constant.CrowdConstant;
import com.zz.crow.mapper.MemberPOMapper;
import com.zz.crow.po.MemberPO;
import com.zz.crow.response.ResultEntity;
import com.zz.crow.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

@RestController
public class MemberProviderController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("get/memberpo/by/loginacct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginacctRemote(@RequestParam("loginacct") String loginacct){

        try {
            MemberPO memberPO = memberService.getMemberPOByLoginacct(loginacct);
            return ResultEntity.successWithData(memberPO);
        }catch (Exception e){
            return ResultEntity.failed(e.getMessage());
        }
    }


    @RequestMapping("save/memberpo/remote")
    ResultEntity<String> saveMemberPORemote(@RequestBody MemberPO memberPO){

        try {
            memberService.saveMemberPO(memberPO);
        }catch (DuplicateKeyException e){
            return ResultEntity.failed(CrowdConstant.USER_ACCOUNT_ALREADY_REGISTER);
        }catch (Exception e){
            return ResultEntity.failed(CrowdConstant.USER_REGISTER_FAILED);
        }
        return ResultEntity.successWithoutData();
    }
}
