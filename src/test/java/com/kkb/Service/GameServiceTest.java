package com.kkb.Service;

import com.github.pagehelper.PageInfo;
import com.kkb.pojo.Game;
import com.kkb.service.GameService;
import com.kkb.vo.QueryGameVo;
import org.aspectj.lang.annotation.DeclareWarning;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class GameServiceTest {
    @Autowired
    private GameService gameService;

    @Test
    public void testQueryAll() throws ParseException {
        QueryGameVo vo = new QueryGameVo();
        vo.setChineseName("勇士");
        vo.setBeginDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-24"));
        vo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-25"));
        vo.setTypeId(2);
        PageInfo<Game> gamePageInfo = gameService.queryByPage(1, 5, vo);
        List<Game> list = gamePageInfo.getList();
        list.forEach(game -> System.out.println(game));
    }
}
