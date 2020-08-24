package com.linzx.redis;

import org.junit.Test;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.params.GeoRadiusParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeoOperateDemo extends Base {

    @Test
    public void geoOp() {
        String key = "geoKey1";
        Map<String, GeoCoordinate> memberCoordinateMap = new HashMap<String, GeoCoordinate>();
        memberCoordinateMap.put("tie_lu_yun_shu", new GeoCoordinate(120.194521,30.256081));
        memberCoordinateMap.put("cheng_zhan_yi_guan", new GeoCoordinate(120.193479,30.256089));
        memberCoordinateMap.put("hang_zhou_cha_chang", new GeoCoordinate(120.196596,30.256362));
        jedis.geoadd("geoKey1", memberCoordinateMap);
        List<GeoCoordinate> geoCoordinateList = jedis.geopos("geoKey1", "tie_lu_yun_shu", "cheng_zhan_yi_guan");
        Double distCalculate = jedis.geodist(key, "tie_lu_yun_shu", "cheng_zhan_yi_guan", GeoUnit.M);

        GeoRadiusParam radiusParam = GeoRadiusParam.geoRadiusParam().withCoord().withDist().sortDescending();
        List<GeoRadiusResponse> geoRadiusResponseList = jedis.georadius("geoKey1", 120.197494,30.254162, 300, GeoUnit.M, radiusParam);
        for (GeoRadiusResponse geoRadiusResponse : geoRadiusResponseList) {
            double distance = geoRadiusResponse.getDistance();
            String member = geoRadiusResponse.getMemberByString();
            GeoCoordinate coordinate = geoRadiusResponse.getCoordinate();
            System.out.println("coordinate:" + coordinate.getLongitude() + "," + coordinate.getLatitude());
        }

        geoRadiusResponseList = jedis.georadiusByMember("geoKey1", "hang_zhou_cha_chang", 100D, GeoUnit.M, radiusParam);
        for (GeoRadiusResponse geoRadiusResponse : geoRadiusResponseList) {
            double distance = geoRadiusResponse.getDistance();
            String member = geoRadiusResponse.getMemberByString();
            GeoCoordinate coordinate = geoRadiusResponse.getCoordinate();
            System.out.println("coordinate:" + coordinate.getLongitude() + "," + coordinate.getLatitude());
        }
        List<String> geohash = jedis.geohash("geoKey1", "tie_lu_yun_shu", "cheng_zhan_yi_guan", "hang_zhou_cha_chang");

        jedis.zrem("geoKey1", "tie_lu_yun_shu");
        jedis.del("geoKey1");

    }

}
