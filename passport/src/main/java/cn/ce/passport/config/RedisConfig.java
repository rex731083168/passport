package cn.ce.passport.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.ce.passport.common.redis.RedisClusterService;
import cn.ce.passport.common.redis.RedisClusterServiceImpl;

@Configuration
public class RedisConfig {

	@Value("${redis.addr}")
	private String redisAddr;
	@Value("${redis.maxActive}")
	private int maxActive;
	@Value("${redis.maxIdle}")
	private int maxIdle;
	@Value("${redis.password}")
	private String password;

	// @Bean(name = "redisService")
	// public RedisService redisService() {
	// RedisServiceImpl redis = new RedisServiceImpl();
	// redis.setRedisAddr(redisAddr);
	// redis.setMaxActive(maxActive);
	// redis.setMaxIdle(maxIdle);
	// redis.setPassword(password);
	// redis.init();
	//
	// return redis;
	// }

	@Bean(name = "redisClusterService")
	public RedisClusterService redisCluService() {
		RedisClusterServiceImpl redisCluService = new RedisClusterServiceImpl();
		redisCluService.setRedisAddr(redisAddr);
		redisCluService.setMaxActive(maxActive);
		redisCluService.setMaxIdle(maxIdle);
		redisCluService.setPassword(password);
		redisCluService.init();

		return redisCluService;
	}
}
