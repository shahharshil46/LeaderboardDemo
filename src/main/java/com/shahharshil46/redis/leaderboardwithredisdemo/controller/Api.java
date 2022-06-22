package com.shahharshil46.redis.leaderboardwithredisdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@Service
@Component
public class Api implements ApplicationListener<ContextRefreshedEvent> {

  @Value("${REDIS_URL}")
  private String redisUrl;

  @Value("${REDIS_HOST}")
  private String redisHost;

  @Value("${REDIS_PORT}")
  private String redisPort;

  @Value("${REDIS_PASSWORD}")
  private String redisPassword;

  @Value("${REDIS_DB}")
  private String redisDB;

  @Value("${REDIS_LEADERBOARD}")
  private String redisLeaderboard;

  @Value("${LEADERBOARD_DATA_READY}")
  private String dataReadyRedisKey;

  @Value("${KEY_PREFIX}")
  private String keyPrefix;

  Jedis jedis;

  @RequestMapping("/api/hello")
  @ResponseBody
  public String getHello(@RequestParam(name = "name") String name) {
    return "Hello " + name;
  }

  @RequestMapping(value = "/api/list/top10", produces = { "text/html; charset=utf-8" })
  @ResponseBody
  public String getTop10() {
    return Utils.getRedisDataZrevrangeWithScores(0, 9, jedis, redisLeaderboard, keyPrefix);
  }


  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    try {
      if (!redisUrl.equals("")) {
        jedis = new JedisPool(redisUrl).getResource();
      } else {
        jedis = new Jedis(redisHost, Integer.parseInt(redisPort));
      }
      if (!redisPassword.equals("")){
        jedis.auth(redisPassword);
      }
      if (!redisDB.equals("")){
        jedis.select(Integer.parseInt(redisDB));
      }
      Utils.resetData(Boolean.parseBoolean(
              jedis.get(dataReadyRedisKey)),
          jedis, dataReadyRedisKey,
          redisLeaderboard, keyPrefix);
    }
    catch (Exception ignored) {
    }
  }
}
