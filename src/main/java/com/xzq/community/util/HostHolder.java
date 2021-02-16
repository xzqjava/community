package com.xzq.community.util;

import com.xzq.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息，用于代替Session对象
 */
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();
    public void setUser(User user){
        users.set(user);
    }
    public User getUser(){
        return users.get();
    }

    /**
     * 清理线程
     */
    public void clear(){
        users.remove();
    }
}
