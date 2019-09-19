package com.ray.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

public class Test02 {
    public static void main(String[] args) {
        //需求:发送大量的微博信息:如:"Hello","ok","bye",但保存最新发布的10条，并将最新微博信息先输出
        Jedis jedis = new Jedis("localhost", 6379);
        for (int i = 10; i < 100; i++) {
            //从Mysql数据库中读取数据微博数据
            jedis.lpush("weibo:userid:1", "hello" + i);
            //如果已经存满10条
            if (jedis.llen("weibo:userid:1") > 10) {
                jedis.ltrim("weibo:userid:1", 0, 9);
            }
        }
        System.out.println("登录输出后。。。。。。");
        List<String> list = jedis.lrange("weibo:userid:1", 0, -1);//-1 最后一个元素
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("登陆输出后删除。。。");
        String result=null;
        while ((result = jedis.lpop("weibo:userid:1")) != null) {
            System.out.println(result);
        }
    }
}
