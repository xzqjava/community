package com.xzq.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


@Component
public class SenstiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SenstiveFilter.class);
    //替换的符号
    private static final String REPLACEMENT="***";

    //初始化树,根节点
    private TrieNode root = new TrieNode();

    @PostConstruct   //服务器启动该方法就被调用
    public void init(){
        InputStream is=null;
        BufferedReader br=null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
            br= new BufferedReader(new InputStreamReader(is));
            String keyword;
            while ((keyword = br.readLine())!=null){
                //添加到前缀树
                this.addKeyWord(keyword);
            }
        } catch (Exception e) {
            logger.error("加载敏感词文件失败"+e.getMessage());
        }finally {
            try {
                is.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //将一个敏感词添加到前缀树中
    private void addKeyWord(String keyword){
        TrieNode temp = root;
        for (int i =0;i<keyword.length();i++){
            char c = keyword.charAt(i);//方法用于返回指定索引处的字符
            TrieNode sonNode = temp.getSonNode(c);
            //判断当前子节点是否为空
            if (sonNode==null){
                //初始化
                sonNode = new TrieNode();
                temp.addSonNode(c,sonNode);
            }
            //指向子节点，继续遍历
            temp=sonNode;
            //设置结束标识
            if (i==keyword.length()-1){
                temp.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     * @param text 待过滤文本
     * @return 过滤后的文本
     */
    public String filter(String text){
        if (StringUtils.isBlank(text)) {
            return null;
        }
        //指针1 指向树节点
        TrieNode temp = root;
        //指针2 指向字符首
        int begin = 0;
        //指针3 指向末尾
        int end = 0;
        //结果
        StringBuilder sb = new StringBuilder();

        while (end<text.length()){
            char c = text.charAt(end);
            //跳过符号
            if (isSymol(c)){
                //如果指针1处于根节点，将此符号j计入结果，让指针2向下走一步
                if (temp==root){
                    sb.append(c);
                    begin++;
                }
                //无论符号在开头或中间，指针3都往下走
                end++;
                continue;
            }
            //检查下级节点
            temp = temp.getSonNode(c);
            if (temp == null){
                //以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                //进入下一个位置
                end = ++begin;
                //重新指向根节点
                temp=root;
            }else if (temp.isKeywordEnd){
                //发现了敏感词  将begin~end替换
                sb.append(REPLACEMENT);
                //进入下一个位置
                begin = ++end;
                //重新指向根节点
                temp=root;
            }else {
                //检查下一个字符
                end++;
            }
        }
        //将最后一批字符计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }
    //判断是否为符号
    private boolean isSymol(Character c){
       return !CharUtils.isAsciiAlphanumeric(c) && (c<0x2E80 || c>0x9FFF);
    }
    private class TrieNode{//字典树
        //关键词结束标识
        private boolean isKeywordEnd=false;

        //当前节点的子节点
        private Map<Character,TrieNode> sonNodes= new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }
        //添加子节点
        public void addSonNode(Character c,TrieNode node){
            sonNodes.put(c,node);
        }
        //获取子节点
        public TrieNode getSonNode(Character c){
            return sonNodes.get(c);
        }
    }
}
