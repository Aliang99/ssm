package com.kkb.vo;

/**
 * 球员页面条件查询的封装对象
 */

public class QueryPlayerVo {

    private String chineseName;
    private Integer type;

    public QueryPlayerVo() {
    }

    public QueryPlayerVo(String chineseName, Integer type) {
        this.chineseName = chineseName;
        this.type = type;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "QueryPlayerVo{" +
                "chineseName='" + chineseName + '\'' +
                ", type=" + type +
                '}';
    }
}
