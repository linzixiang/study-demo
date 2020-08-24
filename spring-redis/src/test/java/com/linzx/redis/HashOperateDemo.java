package com.linzx.redis;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * hash操作
 */
public class HashOperateDemo extends Base {

    @Test
    public void hashOp() {
        jedis.hset("role", "roleId", "1");
        jedis.hset("role", "roleCode", "admin");
        jedis.hset("role", "roleName", "管理员");
        jedis.hset("role", "orderNum", "10");
        Map<String, String> roleMapParam = new HashMap<String, String>();
        roleMapParam.put("status", "1");
        jedis.hmset("role", roleMapParam);
        Boolean roleIdExist = jedis.hexists("role", "roleId");
        Long roleIdSetNx = jedis.hsetnx("role", "roleId", "2");
        String roleId = jedis.hget("role", "roleId");
        List<String> roleVal = jedis.hmget("role", "roleId", "roleCode");
        Map<String, String> roleMap = jedis.hgetAll("role");
        jedis.hincrBy("role", "orderNum", 10);
        String orderNum = jedis.hget("role", "orderNum");
        Set<String> roleKeys = jedis.hkeys("role");
        roleVal = jedis.hvals("role");
    }

}
