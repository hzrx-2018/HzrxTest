package com.hhf.forum.interceptor;

import com.hhf.forum.entity.User;

public class UserContext {
    private static final ThreadLocal<User> current= new ThreadLocal<User>();

    public static void setUser(User user){
        current.set(user);
    }
    public static User getUser(){
        return current.get();
    }
}
