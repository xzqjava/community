package com.xzq.community;

import com.xzq.community.util.SenstiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {
    @Autowired
    private SenstiveFilter senstiveFilter;

    @Test
    public void testSensitiveFilter(){
        String text = "这里可以赌.博,.嫖.娼.,.吸.毒.,.开.票.，嘿嘿";
        String filter = senstiveFilter.filter(text);
        System.out.println(filter);
    }
}
