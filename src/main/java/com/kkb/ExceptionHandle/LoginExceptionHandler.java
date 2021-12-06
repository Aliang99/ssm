package com.kkb.ExceptionHandle;

import com.kkb.Exception.LoginException;
import com.kkb.Exception.LogoutException;
import com.kkb.Exception.NotFoundTeamChineseNameExcption;
import com.kkb.vo.ResultVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class LoginExceptionHandler {

    // 用于处理LoginException的异常
    @ExceptionHandler({LoginException.class})
    @ResponseBody
    public ResultVo loginException(Exception ex){
        return new ResultVo(202,ex.getMessage()); //201表示请求成功，但是没有登录，禁止访问
    }

    // 用于处理LogoutException的异常
    @ExceptionHandler({LogoutException.class})
    @ResponseBody
    public ResultVo logoutException(Exception ex){
        return new ResultVo(202,ex.getMessage()); //201表示请求成功，但是没有登录，禁止访问
    }

    // 用于处理NotFoundTeamChineseNameExcption的异常
    @ExceptionHandler({NotFoundTeamChineseNameExcption.class})
    @ResponseBody
    public ResultVo notFoundTeamChineseNameExcption(Exception ex){
        return new ResultVo(202,ex.getMessage()); //201表示请求成功，但是没有登录，禁止访问
    }
}
