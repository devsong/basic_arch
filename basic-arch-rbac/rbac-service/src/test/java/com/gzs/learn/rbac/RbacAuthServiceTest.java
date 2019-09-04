package com.gzs.learn.rbac;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.gzs.learn.rbac.inf.UserDto;
import com.gzs.learn.rbac.service.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.gzs.learn")
public class RbacAuthServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void testGetByAccount() {
        String account = "admin";
        UserDto user = userService.getUser(account);
        Assert.assertNotNull(user);
    }
}
