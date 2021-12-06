package com.kkb.controller;

import com.github.pagehelper.PageInfo;
import com.kkb.pojo.Team;
import com.kkb.service.TeamService;
import com.kkb.vo.QueryTeamVo;
import com.kkb.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("team")
@ResponseBody
public class TeamController {
    @Autowired
    private TeamService teamService;

    /**
     * 分页条件查询
     * @param pageNum
     * @param pageSize
     * @param vo
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ResultVo<Team> queryByPage(Integer pageNum, Integer pageSize, QueryTeamVo vo){
        if (pageNum==null || pageNum<=0){
            pageNum=1;
        }
        if (pageSize==null || pageSize<=0){
            pageSize=5;
        }
        PageInfo<Team> teamPageInfo = teamService.queryByPage(pageNum, pageSize, vo);
        return new ResultVo<>(teamPageInfo);
    }

    /**
     * 新增球队信息
     * @param team
     * @return
     */
    @RequestMapping(value = "insertTeam",method = RequestMethod.POST)
    public ResultVo<Team> insertTeam(Team team){
        int i = teamService.insertTeam(team);
        if (i!=1){
            return new ResultVo<Team>(500,"服务器内部发生异常");
        }
        return new ResultVo<>();
    }
    /**
     * 根据id查询球队信息
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/{teamId}",method = RequestMethod.GET)
    public ResultVo<Team> queryByTeamId(@PathVariable("teamId") Integer teamId){
        Team team = teamService.queryByTeamId(teamId);
        return new ResultVo<Team>(team);
    }

    /**
     * 更新球队信息
     * @param team
     * @return
     */
    @RequestMapping(value = "/{teamId}",method = RequestMethod.PUT)
    public ResultVo<Team> updateTeam(Team team){
        int i = teamService.updateTeam(team);
        if (i!=1){
            return new ResultVo<Team>(500,"服务器内部发生异常");
        }
        return new ResultVo<>();
    }

    /**
     * 更新球队信息
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/{teamId}",method = RequestMethod.DELETE)
    public ResultVo<Team> deleteTeam(@PathVariable("teamId") Integer teamId){
        int i = teamService.deleteTeam(teamId);
        if (i!=1){
            return new ResultVo<Team>(500,"服务器内部发生异常");
        }
        return new ResultVo<>();
    }
    /**
     * 上传球队logo
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/{teamId}",method = RequestMethod.POST)
    public ResultVo<Team> uploadLogo(@PathVariable("teamId") Integer teamId,
                                     @RequestParam("logo")MultipartFile multipartFile,
                                     HttpServletRequest request){
        String originalFileName = multipartFile.getOriginalFilename(); //上传的文件原始名称
        String[] split = originalFileName.split("\\.");// 符号.需要进行转义
        String hz = split[split.length-1]; //后缀名
        String newFileName = UUID.randomUUID().toString().replace("-", "");
        String path = request.getServletContext().getRealPath("/img/uploadFile/"); //上传的文件夹位置

        try {
            // 上传文件
            multipartFile.transferTo(new File(path+"/"+newFileName+"."+hz));
            Team team = new Team();
            team.setTeamId(teamId);
            team.setTeamLogo(newFileName+"."+hz);
            int i = teamService.updateTeam(team);
            if (i!=1){
                return new ResultVo<Team>(500,"服务器内部发生异常");
            }
            return new ResultVo<>();

        } catch (IOException e) {
            e.printStackTrace();
            return new ResultVo<Team>(500,"文件上传出现错误，请联系管理员小哥哥");
        }
    }
}
