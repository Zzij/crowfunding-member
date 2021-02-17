package com.zz.crow.controller;

import com.netflix.discovery.converters.Auto;
import com.sun.media.jfxmedia.logging.Logger;
import com.zz.crow.api.MysqlRemoteService;
import com.zz.crow.api.RedisRemoteService;
import com.zz.crow.constant.CrowdConstant;
import com.zz.crow.po.MemberPO;
import com.zz.crow.response.ResultEntity;
import com.zz.crow.util.CrowUtil;
import com.zz.crow.vo.MemberLoginVO;
import com.zz.crow.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.util.Enumeration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("auth/member")
@Slf4j
public class MemberController {

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MysqlRemoteService mysqlRemoteService;

    @RequestMapping("/register/send/message")
    @ResponseBody
    public ResultEntity registerSendMessage(@RequestParam("phoneNum") String phoneNum) {

        ResultEntity<String> sendMessageResult = CrowUtil.sendMessage(phoneNum);

        log.info("phone:{}, code:{}", phoneNum, sendMessageResult.getData());
        if (ResultEntity.SUCCESS.equals(sendMessageResult.getResult())) {
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
            ResultEntity<String> setRedisValueWithTimeoutRemoteResult = redisRemoteService.setRedisValueWithTimeoutRemote(key,
                    sendMessageResult.getData(), 60L, TimeUnit.SECONDS);
            if (ResultEntity.SUCCESS.equals(setRedisValueWithTimeoutRemoteResult.getResult())) {
                return ResultEntity.successWithoutData();
            } else {
                return setRedisValueWithTimeoutRemoteResult;
            }
        }
        return sendMessageResult;
    }

    @RequestMapping("/register")
    public String register(MemberVO memberVO, ModelMap modelMap) {
        modelMap.addAttribute("test", "hello");
        modelMap.addAttribute("cn", "中国");
        //1. 根据手机号拿到验证码
        String phoneNum = memberVO.getPhoneNum();
        String redisKey = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        ResultEntity<String> realCode = redisRemoteService.getRedisValueRemote(redisKey);
        if (realCode == null) {
            modelMap.addAttribute("message", CrowdConstant.USER_REGISTER_FAILED);
            return "member-reg";
        }
        if (!ResultEntity.SUCCESS.equals(realCode.getResult())) {
            modelMap.addAttribute("message", realCode.getMessage());
            return "member-reg";
        }
        if (StringUtils.isEmpty(realCode.getData())) {
            modelMap.addAttribute("message", CrowdConstant.REGISTER_CODE_EXPIRE);
            return "member-reg";
        }

        boolean match = Objects.equals(memberVO.getCode(), realCode.getData());
        if (match) {
            redisRemoteService.delRedisValueRemote(redisKey);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encode = bCryptPasswordEncoder.encode(memberVO.getUserpswd());
            memberVO.setUserpswd(encode);
            MemberPO memberPO = new MemberPO();
            BeanUtils.copyProperties(memberVO, memberPO);
            ResultEntity<String> mysqlResult = mysqlRemoteService.saveMemberPORemote(memberPO);
            if (!ResultEntity.SUCCESS.equals(mysqlResult.getResult())) {
                modelMap.addAttribute("message", mysqlResult.getMessage());
                return "member-reg";
            }
            modelMap.addAttribute("message", CrowdConstant.REGISTER_SUCCESS);
        } else {
            modelMap.addAttribute("message", CrowdConstant.REGISTER_CODE_NOT_MATCH);
            return "member-reg";
        }

        return "redirect:http://localhost/auth/member/login/page";
    }

    @RequestMapping("login/page")
    public String memberLoginPage() {
        return "member-login";
    }

    @RequestMapping("login")
    public String memberLogin(@RequestParam("loginacct") String loginacct,
                              @RequestParam("userpswd") String userpswd,
                              ModelMap modelMap,
                              HttpSession session) {
        ResultEntity<MemberPO> memberPOByLoginacctRemote = mysqlRemoteService.getMemberPOByLoginacctRemote(loginacct);

        if (memberPOByLoginacctRemote == null || ResultEntity.FAILED.equals(memberPOByLoginacctRemote.getResult())) {
            modelMap.addAttribute("message", CrowdConstant.LOGIN_FAILED);
            return "member-login";
        }

        MemberPO memberPO = memberPOByLoginacctRemote.getData();
        if (memberPO == null) {
            modelMap.addAttribute("message", CrowdConstant.USER_NOT_EXIST);
            return "member-login";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(userpswd, memberPO.getUserpswd());
        if (!matches) {
            modelMap.addAttribute("message", CrowdConstant.LOGIN_PASSWD_ERROR);
            return "member-login";
        }

        MemberLoginVO memberLoginVO = new MemberLoginVO();
        BeanUtils.copyProperties(memberPO, memberLoginVO);


        session.setAttribute(CrowdConstant.MEMBER_SESSION_NAME, memberLoginVO);


        return "redirect:http://localhost/auth/member/center/page";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:http://localhost/";
    }
}
