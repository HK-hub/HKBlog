package com.hkblog.domain.thread;

import com.hkblog.domain.entity.User;

/**
 * @author : HK意境
 * @ClassName : UserThreadLocal
 * @date : 2021/11/27 19:41
 * @description : 线程变量隔离
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class UserThreadLocal {

    // 私有构造函数，不允许外界构造
    private UserThreadLocal(){}

    // 线程资源
    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static void put(User user){
        LOCAL.set(user);
    }

    public static User get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }



}
