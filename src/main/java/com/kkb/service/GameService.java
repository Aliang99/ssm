package com.kkb.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kkb.Exception.NotFoundTeamChineseNameExcption;
import com.kkb.mapper.GameMapper;
import com.kkb.mapper.TeamMapper;
import com.kkb.pojo.Game;
import com.kkb.pojo.GameExample;
import com.kkb.pojo.Team;
import com.kkb.pojo.TeamExample;
import com.kkb.vo.QueryGameVo;
import com.kkb.vo.UpdateGameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    @Autowired
    public GameMapper gameMapper;
    @Autowired
    public TeamMapper teamMapper;

    /**
     * 分页+条件查询
     * @param pageNum 页码
     * @param pageSize 每页显示数据条数
     * @param vo 查询条件
     * @return
     */
    public PageInfo<Game> queryByPage(Integer pageNum, Integer pageSize, QueryGameVo vo) throws NotFoundTeamChineseNameExcption {
        GameExample example = new GameExample();

        GameExample.Criteria criteriaA = example.createCriteria();
        GameExample.Criteria criteriaB = example.createCriteria();
        //对vo对象的属性值，进行边界判断
        if (vo!=null){
            //开始时间
            if (vo.getBeginDate()!=null){
                criteriaA.andGameDateGreaterThanOrEqualTo(vo.getBeginDate());
                criteriaB.andGameDateGreaterThanOrEqualTo(vo.getBeginDate());
            }
            //结束时间
            if (vo.getEndDate()!=null){
                criteriaA.andGameDateLessThanOrEqualTo(vo.getEndDate());
                criteriaB.andGameDateLessThanOrEqualTo(vo.getEndDate());
            }
            //类型
            if (vo.getTypeId()!=null && vo.getTypeId()!=-1){
                criteriaA.andTypeIdEqualTo(vo.getTypeId());
                criteriaB.andTypeIdEqualTo(vo.getTypeId());
            }
            //球队，分主队和客队，两个都纳入条件
            if(vo.getChineseName()!=null && !"".equals(vo.getChineseName().trim())){
                // 根据球队名称，查询球队id
                TeamExample exampleTeam = new TeamExample();
                TeamExample.Criteria criteriaTeam = exampleTeam.createCriteria();
                criteriaTeam.andChineseNameEqualTo(vo.getChineseName());
                // 查询出符合球队名称的球队id
                List<Team> teams = teamMapper.selectByExample(exampleTeam);
                if (teams.size()==0)
                    throw new NotFoundTeamChineseNameExcption("查询的球队名称不存在");
                //将球队id赋值到where语句的条件判断中,使用or
                teams.forEach(team -> {
                    criteriaA.andHomeTeamIdEqualTo(team.getTeamId());
                    criteriaB.andVisitingTeamIdEqualTo(team.getTeamId());
                });

            }
        }
        example.or(criteriaB);
        PageHelper.startPage(pageNum, pageSize);
        example.setOrderByClause("gameDate desc");
        List<Game> games = gameMapper.selectByExample(example);
        // 注意：
        // 先将集合给到pageInfo后，再对pageInfo内的集合元素操作，
        // 不然总条数会变成pageSize的值
        PageInfo<Game> gamePageInfo = new PageInfo<>(games);
        gamePageInfo.getList().forEach(game -> {
            game.setHomeTeam(teamMapper.selectByPrimaryKey(game.getHomeTeamId())); //根据主队id查询主队中文名称
            game.setVisitingTeam(teamMapper.selectByPrimaryKey(game.getVisitingTeamId())); //根据客队id查询客队中文名称
        });
        return gamePageInfo;
    }

    /**
     * 赛程的添加
     * @param vo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int insertgame(UpdateGameVo vo) throws NotFoundTeamChineseNameExcption {
        Game game = new Game();
        //根据主队的中文名，获取到TeamId，该名称不存在就抛出异常
        TeamExample teamExample1 = new TeamExample();
        teamExample1.createCriteria().andChineseNameEqualTo(vo.getHomeTeam());
        List<Team> teams1 = teamMapper.selectByExample(teamExample1);
        if (teams1.size()==1){
            game.setHomeTeamId(teams1.get(0).getTeamId());
        }else{
            throw new NotFoundTeamChineseNameExcption("填写的主队名称不存在！");
        }
        //根据客队的中文名，获取到TeamId，该名称不存在就抛出异常
        TeamExample teamExample2 = new TeamExample();
        teamExample2.createCriteria().andChineseNameEqualTo(vo.getVisitingTeam());
        List<Team> teams2 = teamMapper.selectByExample(teamExample2);
        if (teams2.size()==1){
            game.setVisitingTeamId(teams2.get(0).getTeamId());
        }else{
            throw new NotFoundTeamChineseNameExcption("填写的客队名称不存在！");
        }
        // 字段不一致，将vo的值，赋到game对象中去，方便使用gameMapper更新数据
        game.setGameId(vo.getGameId());
        game.setHomeTeamScore(vo.getHomeTeamScore());
        game.setVisitingTeamScore(vo.getVisitingTeamScore());
        game.setGameDate(vo.getGameDate());
        game.setTypeId(vo.getType());
        game.setStatus(vo.getStatus());
        return gameMapper.insertSelective(game);
    }

    /**
     * 根据id查询赛程信息
     * @param gameId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Game queryBygameId(Integer gameId){
        Game game = gameMapper.selectByPrimaryKey(gameId);
        if (game!=null){
            Team team = teamMapper.selectByPrimaryKey(game.getHomeTeamId());
            game.setHomeTeam(team);
            Team team1 = teamMapper.selectByPrimaryKey(game.getVisitingTeamId());
            game.setVisitingTeam(team1);
        }
        return game;
    }

    /**
     * 更新赛程信息
     * @param vo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int updategame(UpdateGameVo vo) throws NotFoundTeamChineseNameExcption {
        Game game = new Game();
        //根据主队的中文名，获取到TeamId，该名称不存在就抛出异常
        TeamExample teamExample1 = new TeamExample();
        teamExample1.createCriteria().andChineseNameEqualTo(vo.getHomeTeam());
        List<Team> teams1 = teamMapper.selectByExample(teamExample1);
        if (teams1.size()==1){
            game.setHomeTeamId(teams1.get(0).getTeamId());
        }else{
            throw new NotFoundTeamChineseNameExcption("填写的主队名称不存在！");
        }
        //根据客队的中文名，获取到TeamId，该名称不存在就抛出异常
        TeamExample teamExample2 = new TeamExample();
        teamExample2.createCriteria().andChineseNameEqualTo(vo.getVisitingTeam());
        List<Team> teams2 = teamMapper.selectByExample(teamExample2);
        if (teams2.size()==1){
            game.setVisitingTeamId(teams2.get(0).getTeamId());
        }else{
            throw new NotFoundTeamChineseNameExcption("填写的客队名称不存在！");
        }
        // 字段不一致，将vo的值，赋到game对象中去，方便使用gameMapper更新数据
        game.setGameId(vo.getGameId());
        game.setHomeTeamScore(vo.getHomeTeamScore());
        game.setVisitingTeamScore(vo.getVisitingTeamScore());
        game.setGameDate(vo.getGameDate());
        game.setTypeId(vo.getType());
        game.setStatus(vo.getStatus());

        return gameMapper.updateByPrimaryKeySelective(game);
    }

    /**
     * 删除赛程信息
     *      *逻辑删除，实际上数据还在，只是标记了isDel字段的值
     * @param gameId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int deletegame(Integer gameId){
        Game game = gameMapper.selectByPrimaryKey(gameId);
        game.setIsDel(1);
        return gameMapper.updateByPrimaryKey(game);
    }
}
