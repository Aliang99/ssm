package com.kkb.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 封装赛程更新/新增页面的数据对象
 */
public class UpdateGameVo {
    private Integer gameId;
    private String homeTeam;
    private Integer homeTeamScore;
    private String visitingTeam;
    private Integer visitingTeamScore;
    private Date gameDate;
    private Integer type;
    private Integer status;

    public UpdateGameVo() {
    }

    public UpdateGameVo(Integer gameId, String homeTeam, Integer homeTeamScore, String visitingTeam, Integer visitingTeamScore, Date gameDate, Integer type, Integer status) {
        this.gameId = gameId;
        this.homeTeam = homeTeam;
        this.homeTeamScore = homeTeamScore;
        this.visitingTeam = visitingTeam;
        this.visitingTeamScore = visitingTeamScore;
        this.gameDate = gameDate;
        this.type = type;
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateGameVo{" +
                "gameId=" + gameId +
                ", homeTeam='" + homeTeam + '\'' +
                ", homeTeamScore=" + homeTeamScore +
                ", visitingTeam='" + visitingTeam + '\'' +
                ", visitingTeamScore=" + visitingTeamScore +
                ", gameDate=" + gameDate +
                ", type=" + type +
                ", status=" + status +
                '}';
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public String getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(String visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public Integer getVisitingTeamScore() {
        return visitingTeamScore;
    }

    public void setVisitingTeamScore(Integer visitingTeamScore) {
        this.visitingTeamScore = visitingTeamScore;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) throws ParseException {
        this.gameDate = new SimpleDateFormat("yyyy-MM-dd").parse(gameDate);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
