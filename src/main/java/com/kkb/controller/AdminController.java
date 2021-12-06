package com.kkb.controller;

import com.kkb.Exception.LoginException;
import com.kkb.Exception.LogoutException;
import com.kkb.pojo.Admins;
import com.kkb.service.AdminService;
import com.kkb.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;

@Controller
@RequestMapping("admin")
@ResponseBody
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public ResultVo<Admins> login(Admins admins, HttpServletRequest request) throws LoginException {

        Admins login = adminService.login(admins);
        if (login!=null && login.getLoginName()!=null){
            HttpSession session = request.getSession();
            // 将登录的用户名，以sessionID为键，登录名为值存入session
            session.setAttribute(session.getId(),login.getLoginName());
            return new ResultVo<Admins>(login);
        }else{
            throw new LoginException("登录失败，用户名或密码错误！");
        }
    }
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public ResultVo<Admins> logout(@RequestParam("loginName") String loginName, HttpServletRequest request) throws LogoutException {
        HttpSession session = request.getSession();
        if (loginName!=null && session.getAttribute(session.getId()).equals(loginName)){
            request.getSession().removeAttribute(session.getId());
            return new ResultVo<>();
        }else{
            throw new LogoutException("账号退出失败，服务器内部出现问题！");
        }

    }
}
