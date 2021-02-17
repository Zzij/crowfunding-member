package com.zz.crow;

import com.zz.crow.mapper.MemberPOMapper;
import com.zz.crow.po.MemberPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MysqlTest {

    @Autowired
    private MemberPOMapper memberMapper;

    @Test
    public void demoTest(){

        MemberPO memberPO = new MemberPO();
        memberPO.setUsername("zzj");
        memberPO.setRealname("zzj");
        memberPO.setLoginacct("zzzz");
        memberPO.setUserpswd("zxczc");
        int i = memberMapper.insertSelective(memberPO);
        System.out.println(i);
    }
}
