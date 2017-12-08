package cn.ce.passport.common.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisClusterServiceImpl implements RedisClusterService {
	private Logger logger = LoggerFactory
			.getLogger(RedisClusterServiceImpl.class);

	private String redisAddr;
	private int maxActive = 10;
	private int maxIdle = 5;
	private String password;

	JedisCluster jedis;

	public void setRedisAddr(String redisAddr) {
		this.redisAddr = redisAddr;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void init() {
		// 2017.12.29 fuqy password cluster
		JedisPoolConfig cfg = new JedisPoolConfig();
		cfg.setMaxTotal(maxActive);
		cfg.setMaxIdle(maxIdle);

		String[] serverArray = redisAddr.split(",");
		Set<HostAndPort> nodes = new HashSet<>();

		for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer
					.valueOf(ipPortPair[1].trim())));
		}

		jedis = new JedisCluster(nodes, 2000, 2000, 5, password, cfg);

	}

	public void stop() throws IOException {
		if (jedis != null) {
			jedis.close();
		}
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(String key, String value, int expireSeconds) {
		boolean broken = false;
		try {
			return jedis.setex(key, expireSeconds, value);
		} catch (JedisConnectionException e) {

			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
			}
		}

	}

	@Override
	public String set(byte[] key, byte[] value, int expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long addToSet(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long removeFormSet(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long addToSet(String key, String... value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSetCount(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<String> getSetMembers(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRandomMember(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setIfNotExist(String key, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setIfAbsent(String key, String value, int expireMilliSeconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String get(String key) {
		boolean broken = false;
		try {
			return jedis.get(key);
		} catch (JedisConnectionException e) {

			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
			}
		}
	}

	@Override
	public byte[] get(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> gets(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Double getSetScore(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSortedSet(String key, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int del(String... keys) {
		boolean broken = false;
		try {
			long result = jedis.del(keys);
			return (int) result;
		} catch (JedisConnectionException e) {

			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
			}
		}
	}

	@Override
	public int del(byte[]... keys) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean expire(String key, int seconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean expireAt(String key, long unixTime) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSet(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long incr(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long incrBy(String key, int increment) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Double incrByFloat(String key, double increment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long decr(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long decrBy(String key, int decrement) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void hsets(String key, Map<String, String> fields) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hsetIfNotExists(String key, String field, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> hgets(String key, String... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void hdel(String key, String field) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hdel(String key, String... fields) {
		// TODO Auto-generated method stub

	}

	@Override
	public CASResult<String> cas(String key, Set<String> expectingOriginVals,
			String toBe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String type(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> hkeys(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long substractedAndSet(long minuend, String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long ttl(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long zcount(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long zadd(String key, String member, double score) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long zadd(String key, Map<String, Double> members) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeAndDel(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long zrem(String key, String... member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<String> zreverage(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long hincrBy(String key, String member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long hincrBy(String key, String member, int dlt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long zcard(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long removeSortedSet(String key, String... members) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long llen(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long rpush(String key, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpush(String key, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lpushAndRemveTail(String key, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String lindex(String key, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMember(String key, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long addSet(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> keys(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int batchLpush(String key, List<String> valueList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Jedis getJedisInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sync(Jedis jedis, Pipeline p) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long publish(String channel, String value) {
		// TODO Auto-generated method stub
		return null;
	}

}
