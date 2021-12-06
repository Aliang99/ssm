package com.kkb.vo;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 后端返回到前端的json对象
 * @param <T>
 */
public class ResultVo<T> {
    private PageInfo<T> pageInfo;
    private List<T> lists;
    private T obj;
    private Integer status=200;
    private String msg="ok";

    public ResultVo() {
    }


    public ResultVo(PageInfo<T> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public ResultVo(List<T> lists) {
        this.lists = lists;
    }

    public ResultVo(T obj) {
        this.obj = obj;
    }

    public ResultVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResultVo(String msg) {
        this.msg = msg;
    }

    public PageInfo<T> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<T> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "pageInfo=" + pageInfo +
                ", lists=" + lists +
                ", obj=" + obj +
                ", status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
