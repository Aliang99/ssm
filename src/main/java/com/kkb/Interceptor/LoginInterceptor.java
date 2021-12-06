package com.kkb.Interceptor;


import com.kkb.Exception.LoginException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 登录拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws LoginException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Object userName = request.getSession().getAttribute(request.getSession().getId());
        System.out.println("当前登录用户:"+userName);
        if (userName==null){
            response.sendRedirect("/pages/admin/login.html");
            return false;
        }
        return true;
    }
}
