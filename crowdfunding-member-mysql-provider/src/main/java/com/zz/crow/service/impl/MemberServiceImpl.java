package com.zz.crow.service.impl;

import com.zz.crow.mapper.MemberPOMapper;
import com.zz.crow.po.MemberPO;
import com.zz.crow.po.MemberPOExample;
import com.zz.crow.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginacct(String loginacct) {

        MemberPOExample memberPOExample = new MemberPOExample();
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);
        List<MemberPO> memberPOS = memberPOMapper.selectByExample(memberPOExample);
        return memberPOS.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveMemberPO(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}
