package com.kkb.controller;

import com.github.pagehelper.PageInfo;
import com.kkb.Exception.NotFoundTeamChineseNameExcption;
import com.kkb.pojo.Game;
import com.kkb.service.GameService;
import com.kkb.vo.QueryGameVo;
import com.kkb.vo.ResultVo;
import com.kkb.vo.UpdateGameVo;
import net.sf.jsqlparser.statement.delete.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("game")
@ResponseBody
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * 分页条件查询赛程信息
     * @param pageNum
     * @param pageSize
     * @param vo
     * @return
     */
    @RequestMapping(value = "list",method= RequestMethod.GET)
    public ResultVo<Game> queryByPage(Integer pageNum, Integer pageSize, QueryGameVo vo) throws NotFoundTeamChineseNameExcption {
        System.out.println("VO:"+vo);
        if (pageNum==null || pageNum<=0){
            pageNum=1;
        }
        if (pageSize==null || pageSize<=0){
            pageSize=5;
        }
        PageInfo<Game> gamePageInfo = gameService.queryByPage(pageNum, pageSize, vo);
        System.out.println(gamePageInfo);
        return new ResultVo<>(gamePageInfo);
    }

    /**
     * 根据gameId查询赛程信息
     * @param gameId
     * @return
     */
    @RequestMapping(value = "{gameId}",method = RequestMethod.GET)
    public ResultVo<Game> queryById(@PathVariable("gameId") Integer gameId){
        Game game = gameService.queryBygameId(gameId);
        return new ResultVo<>(game);
    }

    /**
     * 根据gameId更新赛程id
     * @param gameId
     * @param vo
     * @return
     * @throws NotFoundTeamChineseNameExcption
     */
    @RequestMapping(value = "{gameId}",method = RequestMethod.PUT)
    public ResultVo<Game> update(@PathVariable("gameId") Integer gameId, UpdateGameVo vo) throws NotFoundTeamChineseNameExcption {

        int updategame = gameService.updategame(vo);
        if (updategame==1){
            return new ResultVo<>("更新成功");
        }
        return new ResultVo<>(500,"更新失败");
    }

    /**
     * 根据gameId删除赛程信息
     * @param gameId
     * @return
     */
    @RequestMapping(value = "{gameId}",method = RequestMethod.DELETE)
    public ResultVo<Game> delete(@PathVariable("gameId") Integer gameId){
        int deletegame = gameService.deletegame(gameId);
        if (deletegame==1){
            return new ResultVo<>("删除成功");
        }
        return new ResultVo<>(500,"删除失败");
    }

    @RequestMapping(value = "insertGame",method = RequestMethod.POST)
    public ResultVo<Game> insert(UpdateGameVo vo) throws NotFoundTeamChineseNameExcption {
        int insertgame = gameService.insertgame(vo);
        if (insertgame==1){
            return new ResultVo<>("添加成功");
        }
        return new ResultVo<>(500,"添加失败");
    }

}
