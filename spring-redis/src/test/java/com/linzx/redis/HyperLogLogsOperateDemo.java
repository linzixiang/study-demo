package com.linzx.redis;

import org.junit.Test;

public class HyperLogLogsOperateDemo extends Base {

    @Test
    public void hyperLogLogsOp() {
        jedis.pfadd("hyperLogLogsKey1", "a", "b", "c", "d", "a", "c");
        jedis.pfadd("hyperLogLogsKey2", "a", "b", "c", "d", "e", "f");
        long pfcount = jedis.pfcount("hyperLogLogsKey1", "hyperLogLogsKey2");
        jedis.pfmerge("hyperLogLogsKey3", "hyperLogLogsKey1", "hyperLogLogsKey2");
        pfcount = jedis.pfcount("hyperLogLogsKey3");
        jedis.del("hyperLogLogsKey1", "hyperLogLogsKey2", "hyperLogLogsKey3");
    }

}
