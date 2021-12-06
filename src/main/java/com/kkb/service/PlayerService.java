package com.kkb.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kkb.Exception.NotFoundTeamChineseNameExcption;
import com.kkb.mapper.PlayerMapper;
import com.kkb.mapper.TeamMapper;
import com.kkb.pojo.Player;
import com.kkb.pojo.PlayerExample;
import com.kkb.pojo.Team;
import com.kkb.pojo.TeamExample;
import com.kkb.vo.QueryPlayerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private PlayerMapper playerMapper;
    /**
     * 分页+条件查询
     * @return
     */
    public PageInfo<Player> queryByPage(Integer pageNum, Integer pageSize, QueryPlayerVo vo) throws NotFoundTeamChineseNameExcption {
        PlayerExample example = new PlayerExample();
        PlayerExample.Criteria criteria = example.createCriteria();
        //对vo对象的属性值，进行边界判断
        if (vo!=null){
            //球队名称
            if (vo.getChineseName()!=null && !"".equals(vo.getChineseName().trim())){
                TeamExample teamExample = new TeamExample();
                teamExample.createCriteria().andChineseNameEqualTo(vo.getChineseName());
                List<Team> teams = teamMapper.selectByExample(teamExample);
                if (teams.size()!=0)
                    criteria.andTeamIdEqualTo(teams.get(0).getTeamId());
                else
                    throw new NotFoundTeamChineseNameExcption("查询的球队名称不存在");
            }
            //类型
            if (vo.getType()!=null && vo.getType()!=-1){
                criteria.andStatusEqualTo(vo.getType());
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        example.setOrderByClause("playerId");
        List<Player> Players = playerMapper.selectByExample(example);
        // 注意：
        // 先将集合给到pageInfo后，再对pageInfo内的集合元素操作，
        // 不然总条数会变成pageSize的值
        PageInfo<Player> playerPageInfo = new PageInfo<>(Players);
        playerPageInfo.getList().forEach(player -> {
            String chineseName = teamMapper.selectByPrimaryKey(player.getTeamId()).getChineseName();
            player.setTeamName(chineseName); //根据主队id查询主队中文名称
        });
        return playerPageInfo;
    }

    /**
     * 球员的添加
     * @param player
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int insertPlayer(Player player) throws NotFoundTeamChineseNameExcption {
        //根据主队的中文名，获取到TeamId，该名称不存在就抛出异常
        PlayerExample playerExample = new PlayerExample();
        TeamExample teamExample = new TeamExample();
        teamExample.createCriteria().andChineseNameEqualTo(player.getTeamName());
        List<Team> teams = teamMapper.selectByExample(teamExample);
        if (teams.size()==0){
            throw new NotFoundTeamChineseNameExcption("添加时指定的球队名称不存在！");
        }
        player.setTeamId(teams.get(0).getTeamId());
        if (player.getIsDel()==null)
            player.setIsDel(0);
       return playerMapper.insert(player);
    }

    /**
     * 根据id查询赛程信息
     * @param playerId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Player queryByPlayerId(Integer playerId){
        Player player = playerMapper.selectByPrimaryKey(playerId);
        if (player!=null){
            player.setTeamName(teamMapper.selectByPrimaryKey(player.getTeamId()).getChineseName());
        }
        return player;
    }

    /**
     * 更新赛程信息
     * @param player
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int updatePlayer(Player player) throws NotFoundTeamChineseNameExcption {
        //根据主队的中文名，获取到TeamId，该名称不存在就抛出异常
        TeamExample teamExample = new TeamExample();
        teamExample.createCriteria().andChineseNameEqualTo(player.getTeamName());
        List<Team> teams = teamMapper.selectByExample(teamExample);
        if (teams.size()==0){
            throw new NotFoundTeamChineseNameExcption("更新时指定的球队名称不存在！");
        }
        player.setTeamId(teams.get(0).getTeamId());
        return playerMapper.updateByPrimaryKeySelective(player);
    }

    /**
     * 删除赛程信息
     *      *逻辑删除，实际上数据还在，只是标记了isDel字段的值
     * @param playerId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int deletePlayer(Integer playerId){
        Player player = playerMapper.selectByPrimaryKey(playerId);
        player.setIsDel(1);
        return playerMapper.updateByPrimaryKey(player);
    }
}
