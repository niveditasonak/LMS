package com.elmo.spring.persistence.helpers;

import framework.Toolbox;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by VB on 12/1/2015.
 */
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception
    {
        String uri = request.getRequestURI();

            HttpSession sess = Toolbox.getSession();
            if(sess == null)
            {
                response.sendRedirect("/elmo/logout");
                return false;
            }

        return true;
    }
}
