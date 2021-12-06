package com.kkb.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 比赛列表页面的条件查询的封装对象
 */
public class QueryGameVo {

    private String chineseName;//球队名称
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;//开始时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;//结束时间
    private Integer typeId;

    public QueryGameVo() {
    }

    public QueryGameVo(String chineseName, Date beginDate, Date endDate, Integer typeId) {
        this.chineseName = chineseName;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.typeId = typeId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "QueryGameVo{" +
                "chineseName='" + chineseName + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", typeId=" + typeId +
                '}';
    }
}
