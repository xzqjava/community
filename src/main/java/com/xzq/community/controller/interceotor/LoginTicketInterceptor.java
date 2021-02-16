package com.xzq.community.controller.interceotor;

import com.xzq.community.entity.LoginTicket;
import com.xzq.community.entity.User;
import com.xzq.community.service.UserService;
import com.xzq.community.util.CookieUtil;
import com.xzq.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Override                       //在Controller处理之前调用这个方法
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
        //从cookie中获取凭证
        String ticket = CookieUtil.getValue(request,"ticket");
        if (ticket !=null){
            //查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            //检查凭证是否有效                                                超时时间晚于当前时间
            if (loginTicket !=null&&loginTicket.getStatus()==0&&loginTicket.getExpired().after(new Date())){
                //根据凭证查询用户
                User user = userService.findUserById(loginTicket.getUserId());
                //在本次请求持有用户
                hostHolder.setUser(user); //暂存到线程对象里
            }
        }
        return true;
    }
    @Override
    //postHandle在模板引擎之前、Controller层之后调用
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception{
        User user = hostHolder.getUser();
        if (user!=null&&modelAndView!=null){
            modelAndView.addObject("loginUser",user);
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                           Object handler, Exception ex) throws Exception{
       hostHolder.clear();
    }

}
