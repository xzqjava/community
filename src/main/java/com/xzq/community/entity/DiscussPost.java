package com.xzq.community.entity;

import lombok.Data;

import java.util.Date;
//帖子类
@Data
public class DiscussPost {
    private int id;
    private int userId;
    private String title;
    private String content;//内容
    private int dtype;
    private int status;//0是正常 1是精华 2是拉黑
    private Date createTime;
    private int commentCount;//评论数量
    private double score;//帖子分数
}
