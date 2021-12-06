package com.kkb.service;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kkb.mapper.TeamMapper;
import com.kkb.pojo.Team;
import com.kkb.pojo.TeamExample;
import com.kkb.vo.QueryTeamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class TeamService {

    @Autowired
    public TeamMapper teamMapper;

    /**
     * 分页+条件查询
     * @param pageNum 页码
     * @param pageSize 每页显示数据条数
     * @param vo 查询条件
     * @return
     */
    public PageInfo<Team> queryByPage(Integer pageNum, Integer pageSize, QueryTeamVo vo){
        TeamExample example = new TeamExample();
        TeamExample.Criteria criteria = example.createCriteria();
        //对vo对象的属性值，进行边界判断
        if (vo!=null){
            //球队名
            if(vo.getTeamName()!=null && !"".equals(vo.getTeamName().trim())){
                criteria.andTeamNameLike("%"+vo.getTeamName().trim()+"%");
            }
            //中文名
            if(vo.getChineseName()!=null && !"".equals(vo.getChineseName().trim())){
                criteria.andChineseNameLike("%"+vo.getChineseName().trim()+"%");
            }
            //教练名
            if(vo.getCoachName()!=null && !"".equals(vo.getCoachName().trim())){
                criteria.andCoachLike("%"+vo.getCoachName().trim()+"%");
            }
            //开始时间
            if (vo.getBeginDate()!=null){
                criteria.andCreateTimeGreaterThanOrEqualTo(vo.getBeginDate());
            }
            //结束时间
            if (vo.getEndDate()!=null){
                criteria.andCreateTimeLessThanOrEqualTo(vo.getEndDate());
            }
            //联盟
            if (vo.getArea()!=null && (vo.getArea()==1 || vo.getArea()==0)){
                criteria.andAreaEqualTo(vo.getArea());
            }
        }
        example.setOrderByClause("teamId");
        PageHelper.startPage(pageNum, pageSize);
        List<Team> teams = teamMapper.selectByExample(example);
        System.out.println(teams);
        return new PageInfo<>(teams);
    }

    /**
     * 球队的添加
     * @param team
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int insertTeam(Team team){
        return teamMapper.insertSelective(team);
    }

    /**
     * 根据id查询球队信息
     * @param teamId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Team queryByTeamId(Integer teamId){
        return teamMapper.selectByPrimaryKey(teamId);
    }

    /**
     * 更新球队信息
     * @param team
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int updateTeam(Team team){
        return teamMapper.updateByPrimaryKeySelective(team);
    }

    /**
     * 删除球队信息
     *      *逻辑删除，实际上数据还在，只是标记了isDel字段的值
     * @param teamId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public int deleteTeam(Integer teamId){
        Team team = teamMapper.selectByPrimaryKey(teamId);
        team.setIsDel(1);
        return teamMapper.updateByPrimaryKey(team);
    }
}
