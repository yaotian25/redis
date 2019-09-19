package com.ray.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class Test03 {
    /**
     * 需求:10个用户发出大量的微博信息:如"OK"等
     * 但请求只保存用户发布最新的10条微博信息，并将最新的微博信息先输出
     */
    public static void main(String[] args) {
        Jedis jedis=new Jedis("localhost",6379);
        Random rd=new Random();
        for(int i=0;i<100;i++){
            int userid=rd.nextInt(10)+1;
            jedis.lpush("weibo:userid:"+userid, "hello world"+i);
            if(jedis.llen("weibo:userid:"+userid)>10){
                jedis.ltrim("weibo:userid:", 0, 9);
            }
        }

        //获取微博所有键
        Set<String> weiboKeys=jedis.keys("weibo:userid:*");
        for(String webKey:weiboKeys){
            List<String> list=jedis.lrange(webKey, 0, -1);
            System.out.println("用户为："+webKey+"发的信息如下：");
            for(String s:list){
                System.out.println(s+"\t");
            }
        }
    }
}
