package com.linzx.redis;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class SortSetOperateDemo extends Base {

    @Test
    public void sortSetOp() {
        String key = "sortSetKey1";
        Map<String, Double> sortSetMap = new HashMap<String, Double>();
        for (int i = 0; i < 20; i++) {
            sortSetMap.put("mapKey" + i, new Random().nextDouble() * 100);
        }
        sortSetMap.size();
        jedis.zadd(key, sortSetMap);
        Double mapKey1Incr = jedis.zincrby(key, 20D, "mapKey1");
        Long sortSetKey1Count = jedis.zcard(key); // 计数
        Long sortSetKey1Count_10_50 = jedis.zcount(key, 10D, 50D); // 计算[10, 50]范围内的数量
        System.out.println("按score递增，所有成员：" + jedis.zrange(key, 0, -1));
        Set<String> sortSetKey1Range = jedis.zrange(key, 10, 15);// 返回[10, 15] 区间内的成员，成员的位置按 score 值递增
        System.out.println("按score递增，区间[10,15]成员：" + sortSetKey1Range);
        Set<String> sortSetKey1ReRange = jedis.zrevrange(key, 10, 15);// 返回[10, 15] 区间内的成员，成员的位置按score值递减
        System.out.println("按score递减，区间[10,15]成员：" + sortSetKey1ReRange);
        Set<String> sortSetKey1RangeByScore = jedis.zrangeByScore(key, 10D, 50D);
        System.out.println("score在区间[10,50]，按score递增：" + sortSetKey1RangeByScore);
        Set<String> sortSetKey1ReRangeByScore = jedis.zrevrangeByScore(key, 10D, 50D);
        System.out.println("score在区间[10,50]，按score递减：" + sortSetKey1ReRangeByScore);
        Long mapKey10Rank = jedis.zrank(key, "mapKey10"); // 成员mapKey10的排名（有序集成员按 score 值递增）
        System.out.println("有序集成员按 score 值递增，mapKey10排名：" + mapKey10Rank);
        Long mapKey10ReRank = jedis.zrevrank(key, "mapKey10");// 成员mapKey10的排名（有序集成员按 score 值递减）
        System.out.println("有序集成员按 score 值递减，mapKey10排名：" + mapKey10ReRank);
        jedis.zrem(key, "mapKey1"); // 移除mapKey1
        System.out.println("移除mapKey1，所有成员：" + jedis.zrange(key, 0, -1));
        jedis.zremrangeByRank(key, 1, 5); // 移除排名[1,5]区间内的成员
        System.out.println("所有成员：" + jedis.zrange(key, 0, -1));
        jedis.zremrangeByScore(key, 10D, 20D); // 移除分数值在[10,20]区间内的成员
        System.out.println("所有成员：" + jedis.zrange(key, 0, -1));
    }

}
