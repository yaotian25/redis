package com.ray.redis;

import redis.clients.jedis.Jedis;

public class Test01 {
    public static void main(String[] args) {
        //抽奖小案例
        Jedis jedis = new Jedis("localhost", 6379);
       // System.out.println(jedis);
        for (int i = 0; i < 10; i++) {
            jedis.set("Ray" + (i + 1), "RayLeo" + (i + 1));
        }
        String key=null;
        //因为当前库中所以进行了筛选
        do {
            key = jedis.randomKey();
        } while (!key.startsWith("Ray"));
       // System.out.println(key);
        String username=jedis.get(key);
        System.out.println("当前幸运用户："+username);
    }
}
