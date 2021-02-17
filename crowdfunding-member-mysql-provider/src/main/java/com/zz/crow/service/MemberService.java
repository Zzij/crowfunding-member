package com.zz.crow.service;

import com.zz.crow.po.MemberPO;

public interface MemberService {
    MemberPO getMemberPOByLoginacct(String loginacct);

    void saveMemberPO(MemberPO memberPO);
}
