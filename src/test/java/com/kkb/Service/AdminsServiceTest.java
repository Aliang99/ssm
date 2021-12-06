package com.kkb.Service;


import com.kkb.pojo.Admins;
import com.kkb.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class AdminsServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void test(){
        Admins admin = new Admins();
        admin.setLoginName("admin");
        admin.setAdminPwd("bushuo");
        Admins login = adminService.login(admin);
        System.out.println(login);
    }
}
