package com.kkb.Service;

import com.github.pagehelper.PageInfo;
import com.kkb.Exception.NotFoundTeamChineseNameExcption;
import com.kkb.pojo.Player;
import com.kkb.service.PlayerService;
import com.kkb.vo.QueryPlayerVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    /**
     * 分页测试
     * @throws NotFoundTeamChineseNameExcption
     */
    @Test
    public void testPageIngoPlayer() throws NotFoundTeamChineseNameExcption {
        Integer pageNum=1;
        Integer pageSize=5;
        QueryPlayerVo vo = new QueryPlayerVo();
        vo.setTeamName("勇士");
        vo.setType(1);
        PageInfo<Player> playerPageInfo = playerService.queryByPage(pageNum, pageSize, vo);
        playerPageInfo.getList().forEach(player -> System.out.println(player));
    }

    /**
     * 查询测试
     */
    @Test
    public void queryById(){
        Integer id=1;
        Player player = playerService.queryByPlayerId(id);
        System.out.println(player);
    }

    /**
     * 更新测试
     */
    @Test
    public void updatetest() throws ParseException, NotFoundTeamChineseNameExcption {
        Player player = new Player();
        player.setPlayerId(9);
        player.setPlayerName("测试名称");
        player.setPlayerNum(22);
        player.setLocation("后卫");
        player.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1998-07-02"));
        player.setNationality("CN");
        player.setHeight(180.0);
        player.setWeight(77.0);
        player.setSalary(1000.0);
        player.setTeamName("骑士");
        player.setStatus(1);
        int i = playerService.updatePlayer(player);
        System.out.println(i);
    }
    /**
     * 插入测试
     */
    @Test
    public void addtest() throws NotFoundTeamChineseNameExcption, ParseException {
        Player player = new Player();
        player.setPlayerName("测试名称1");
        player.setPlayerNum(22);
        player.setLocation("后卫1");
        player.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1998-07-02"));
        player.setNationality("CN1");
        player.setHeight(181.0);
        player.setWeight(71.0);
        player.setSalary(1001.0);
        player.setTeamName("勇士");
        player.setStatus(1);
        int i = playerService.insertPlayer(player);
        System.out.println(i);
    }
    /**
     * 删除测试
     */
    @Test
    public void deletetest(){
        int i = playerService.deletePlayer(9);
        System.out.println(i);
    }
}
