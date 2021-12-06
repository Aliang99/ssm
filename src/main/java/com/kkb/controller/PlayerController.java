package com.kkb.controller;

import com.github.pagehelper.PageInfo;
import com.kkb.Exception.NotFoundTeamChineseNameExcption;
import com.kkb.pojo.Player;
import com.kkb.service.PlayerService;
import com.kkb.vo.QueryPlayerVo;
import com.kkb.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("player")
@ResponseBody
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    /**
     * 分页+条件查询
     * @param pageNum
     * @param pageSize
     * @param vo
     * @return
     * @throws NotFoundTeamChineseNameExcption
     */
    @RequestMapping(value = "list",method= RequestMethod.GET)
    public ResultVo<Player> queryByPage(Integer pageNum, Integer pageSize, QueryPlayerVo vo) throws NotFoundTeamChineseNameExcption {
        System.out.println("VO:"+vo);
        if (pageNum==null || pageNum<=0){
            pageNum=1;
        }
        if (pageSize==null || pageSize<=0){
            pageSize=5;
        }
        PageInfo<Player> playerPageInfo = playerService.queryByPage(pageNum, pageSize, vo);
        System.out.println(playerPageInfo);
        return new ResultVo<>(playerPageInfo);
    }
    /**
     * 根据playerId查询赛程信息
     * @param playerId
     * @return
     */
    @RequestMapping(value = "{playerId}",method = RequestMethod.GET)
    public ResultVo<Player> queryById(@PathVariable("playerId") Integer playerId){
        Player player = playerService.queryByPlayerId(playerId);
        return new ResultVo<>(player);
    }

    /**
     * 根据playerId更新球员信息
     * @param gameId
     * @param player
     * @return
     * @throws NotFoundTeamChineseNameExcption
     */
    @RequestMapping(value = "{playerId}",method = RequestMethod.PUT)
    public ResultVo<Player> update(@PathVariable("playerId") Integer gameId, Player player) throws NotFoundTeamChineseNameExcption {

        int updatePlayer = playerService.updatePlayer(player);
        if (updatePlayer==1){
            return new ResultVo<>("更新成功");
        }
        return new ResultVo<>(500,"更新失败");
    }

    /**
     * 根据playerId删除球员信息
     * @param playerId
     * @return
     */
    @RequestMapping(value = "{playerId}",method = RequestMethod.DELETE)
    public ResultVo<Player> delete(@PathVariable("playerId") Integer playerId){
        int deletePlayer = playerService.deletePlayer(playerId);
        if (deletePlayer==1){
            return new ResultVo<>("删除成功");
        }
        return new ResultVo<>(500,"删除失败");
    }

    /**
     * 添加球员信息
     * @param player
     * @return
     * @throws NotFoundTeamChineseNameExcption
     */
    @RequestMapping(value = "insertPlayer",method = RequestMethod.POST)
    public ResultVo<Player> insert(Player player) throws NotFoundTeamChineseNameExcption {
        int insertPlayer = playerService.insertPlayer(player);
        if (insertPlayer==1){
            return new ResultVo<>("添加成功");
        }
        return new ResultVo<>(500,"添加失败");
    }
}
