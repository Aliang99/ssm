package com.kkb;

import com.kkb.mapper.PlayerMapper;
import com.kkb.pojo.Player;

import com.kkb.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestMapper {
    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private TeamService teamService;
    /**
     * 查询全部的player表数据
     */
    @Test
    public void test1() {
        List<Player> players = playerMapper.selectByExample(null);
        players.forEach(player -> System.out.println(player));
    }
}
