package com.xzq.community.util;

public interface CommunityConstant {
    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT=0;
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS=1;
    /**
     * 失败
     */
    int ACTIVATION_FAILURE=2;
    /**
     * 默认状态登陆凭证超时时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600*12;
    /**
     * 记住状态下登陆凭证超时时间
     */
    int REMEMBER_EXPIRED_SECONDS = 3600*24*100;
    /**
     * 实体类型：帖子
     */
    int ENTITY_TYPE_POST = 1;

    /**
     * 实体类型：评论
     */
    int ENTITY_TYPE_COMMENT = 2;
    /**
     * 用户
     */
    int ENTITY_TYPE_USER=3;
}
