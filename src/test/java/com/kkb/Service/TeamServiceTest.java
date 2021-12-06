package com.kkb.Service;

import com.github.pagehelper.PageInfo;
import com.kkb.pojo.Team;
import com.kkb.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TeamServiceTest {
    @Autowired
    private TeamService teamService ;
    @Test
    public void Test1(){
        PageInfo<Team> teamPageInfo = teamService.queryByPage(1, 5,null);
        System.out.println(teamPageInfo);

    }
}
