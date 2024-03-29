package cn.ce.passport.common.redis;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisServiceImpl implements RedisService {
	private Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
	// redis lua scripts(supportted since redis 2.6)
	private static final String CAS_CMD = "local v=redis.call('get',KEYS[1]);local r=v;local n=#ARGV-1;local tb=ARGV[#ARGV];local succ='F';for i=1, n do if(v==ARGV[i]) then redis.call('set',KEYS[1],tb); r=tb; succ='T' break;end end;return {succ,r}";
	private static final String SUBS_CMD = "local v=redis.call('incrby',KEYS[1],0);local r=ARGV[1]-v;redis.call('set',KEYS[1],ARGV[1]);return r;";
	private String CAS_KEY;
	private String SUBS_KEY;
	private String redisAddr;
	private String[] addrInfo;
	private JedisPool jedisPool;
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
		if (redisAddr == null || "".equals(redisAddr.trim())
				|| "NULL".equals(redisAddr.trim())) {
			logger.warn("no redis addr was configured,this redis service will be unavaliable");
			return;
		}
		JedisPoolConfig cfg = new JedisPoolConfig();
		cfg.setMaxTotal(maxActive);
		cfg.setMaxIdle(maxIdle);
		addrInfo = redisAddr.split(":");
		// 2017.12.29 fuqy password
		jedisPool = new JedisPool(cfg, addrInfo[0],
				Integer.valueOf(addrInfo[1]), 30000, password, 0);
		// load cas scripts

		Jedis jedis = jedisPool.getResource();

		try {
			CAS_KEY = jedis.scriptLoad(CAS_CMD);
			SUBS_KEY = jedis.scriptLoad(SUBS_CMD);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

//集群操作在另一个
//	public void initCluster() {
//		
//		JedisPoolConfig cfg = new JedisPoolConfig();
//		cfg.setMaxTotal(maxActive);
//		cfg.setMaxIdle(maxIdle);
//
//		addrInfo = redisAddr.split(":");
//		// 2017.12.29 fuqy password
//		jedisPool = new JedisPool(cfg, addrInfo[0],
//				Integer.valueOf(addrInfo[1]), 30000, password, 0);
//		// load cas scripts
//
//		Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
//		nodes.add(new HostAndPort("10.12.40.161", 6001));
//		nodes.add(new HostAndPort("10.12.40.161", 5001));
//		nodes.add(new HostAndPort("10.12.40.161", 7001));
//
//	    jedis = new JedisCluster(nodes, 2000, 2000, 5, password,
//				cfg);
//
//	}

	public void stop() {
		if (jedisPool != null) {
			jedisPool.destroy();
		}
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.set(key, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public String set(byte[] key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.set(key, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	/**
	 * 以当前时间的毫秒数位score
	 */
	@Override
	public void addSortedSet(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			jedis.zadd(key, System.currentTimeMillis(), value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public String set(String key, String value, int expireSeconds) {
//		Jedis jedis = jedisPool.getResource();
//		boolean broken = false;
//		try {
//			return jedis.setex(key, expireSeconds, value);
//		} catch (JedisConnectionException e) {
//			jedisPool.returnBrokenResource(jedis);
//			broken = true;
//			throw e;
//		} finally {
//			if (jedis != null && !broken) {
//				jedisPool.returnResource(jedis);
//			}
//		}
		
		
		boolean broken = false;
		try {
			return jedis.setex(key, expireSeconds, value);
		} catch (JedisConnectionException e) {
//			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
//				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public String set(byte[] key, byte[] value, int expireSeconds) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.setex(key, expireSeconds, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public Long addToSet(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.sadd(key, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	public Long removeFormSet(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.srem(key, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public Long addToSet(String key, String... value) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.sadd(key, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public int getSetCount(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.scard(key).intValue();
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public boolean setIfNotExist(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			long result = jedis.setnx(key, value);
			return result > 0;
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.get(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public byte[] get(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.get(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public List<String> gets(String... keys) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.mget(keys);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.exists(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public Double getSetScore(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.zscore(key, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public int del(String... keys) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			long result = jedis.del(keys);
			return (int) result;
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public int del(byte[]... keys) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			long result = jedis.del(keys);
			return (int) result;
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public boolean expire(String key, int seconds) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			long result = jedis.expire(key, seconds);
			return result > 0;
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public boolean expireAt(String key, long unixTime) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			long result = jedis.expireAt(key, unixTime);
			return result > 0;
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public String getSet(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.getSet(key, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	public Set<String> getSetMembers(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.smembers(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public String getRandomMember(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.srandmember(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.incr(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long incrBy(String key, int increment) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.incrBy(key, increment);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public Double incrByFloat(String key, double increment) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.incrByFloat(key, increment);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long decr(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.decr(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long decrBy(String key, int decrement) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.decrBy(key, decrement);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public int hset(String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		int result = 0;
		boolean broken = false;
		try {
			result = jedis.hset(key, field, value).intValue();
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
		return result;
	}

	@Override
	public CASResult<String> cas(String key, Set<String> expectingOriginVals,
			String toBe) {
		List<String> args = new ArrayList<String>(
				expectingOriginVals.size() + 2);
		args.add(key);
		args.addAll(expectingOriginVals);
		args.add(toBe);
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			@SuppressWarnings("unchecked")
			List<String> response = (List<String>) jedis.evalsha(CAS_KEY, 1,
					args.toArray(new String[0]));
			String succStr = response.get(0);
			String val = response.get(1);
			CASResult<String> result = new CASResult<String>(
					"T".equals(succStr), val);
			return result;
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long substractedAndSet(long minuend, String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			Long response = (Long) jedis.evalsha(SUBS_KEY, 1, key,
					String.valueOf(minuend));
			return response;
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public void hsets(String key, Map<String, String> fields) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			jedis.hmset(key, fields);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public boolean hsetIfNotExists(String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		long result = 0;
		boolean broken = false;
		try {
			result = jedis.hsetnx(key, field, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
		return result > 0;
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		String val = null;
		boolean broken = false;
		try {
			val = jedis.hget(key, field);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
		return val;
	}

	@Override
	public List<String> hgets(String key, String... fields) {
		Jedis jedis = jedisPool.getResource();
		List<String> val = null;
		boolean broken = false;
		try {
			val = jedis.hmget(key, fields);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
		return val;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		Jedis jedis = jedisPool.getResource();
		Map<String, String> val = null;
		boolean broken = false;
		try {
			val = jedis.hgetAll(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
		return val;
	}

	@Override
	public void hdel(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			jedis.hdel(key, field);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public void hdel(String key, String... fields) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			jedis.hdel(key, fields);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public String type(String key) {
		Jedis jedis = jedisPool.getResource();
		String val = null;
		boolean broken = false;
		try {
			val = jedis.type(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
		return val;

	}

	@Override
	public Set<String> hkeys(String key) {
		Jedis jedis = jedisPool.getResource();
		Set<String> val = null;
		boolean broken = false;
		try {
			val = jedis.hkeys(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
		return val;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis = jedisPool.getResource();
		String val = null;
		boolean broken = false;
		try {
			val = jedis.hmset(key, hash);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
		return val;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		long val = -99;
		boolean broken = false;
		try {
			val = jedis.ttl(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
		return val;
	}

	@Override
	public Long zcount(String key, double min, double max) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.zcount(key, min, max);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long zadd(String key, String member, double score) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.zadd(key, score, member);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long zadd(String key, Map<String, Double> members) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {

			return jedis.zadd(key, members);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.zrange(key, start, end);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrangeAndDel(String key, long start, long end) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			Transaction tx = jedis.multi();
			tx.zrange(key, start, end);
			tx.del(key);
			List<Object> resultList = tx.exec();
			return (Set<String>) resultList.get(0);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public Set<String> zreverage(String key, long start, long end) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.zrevrange(key, start, end);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long zrem(String key, String... member) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.zrem(key, member);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	public String toString() {
		return "redis addr :" + redisAddr;
	}

	@Override
	public long hincrBy(String key, String member) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.hincrBy(key, member, 1);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long hincrBy(String key, String member, int dlt) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.hincrBy(key, member, dlt);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long zcard(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.zcard(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public boolean setIfAbsent(String key, String value, int expireMilliSeconds) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			String result = jedis.set(key, value, "nx", "px",
					expireMilliSeconds);
			if (result != null && result.equalsIgnoreCase("OK")) {
				return true;
			}
			return false;
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public long removeSortedSet(String key, String... members) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.zrem(key, members);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	public String getRedisAddr() {
		return redisAddr;
	}

	@Override
	public long llen(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.llen(key);

		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public List<String> lrange(String key, final long start, final long end) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.lrange(key, start, end);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public String rpop(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.rpop(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public Long lpush(String key, String string) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.lpush(key, string);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public String lpop(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.lpop(key);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public Long rpush(String key, String string) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.rpush(key, string);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public boolean isMember(String key, String value) {

		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.sismember(key, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}

	}

	@Override
	public Long addSet(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.sadd(key, value);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}

	}

	@Override
	public Set<String> keys(String pattern) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.keys(pattern);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}

	}

	@Override
	public int batchLpush(String key, List<String> valueList) {
		if (key == null || valueList == null)
			return 0;

		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			Pipeline p = jedis.pipelined();
			for (String value : valueList) {
				p.lpush(key, value);
			}
			p.sync();
			return valueList.size();
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	@Override
	public void lpushAndRemveTail(String key, String value) {
		// TODO Auto-generated method stub
		// 只为编译通过，请选择主干覆盖该方法

	}

	@Override
	public String lindex(String key, int index) {
		// TODO Auto-generated method stub
		// 只为编译通过，请选择主干覆盖该方法
		return null;
	}

	public Jedis getJedisInstance() {
		return jedisPool.getResource();
	}

	public void sync(Jedis jedis, Pipeline p) {
		if (jedis == null || p == null) {
			return;
		}

		boolean broken = false;

		try {
			p.sync();
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	public Long publish(String channel, String message) {
		Jedis jedis = jedisPool.getResource();
		boolean broken = false;
		try {
			return jedis.publish(channel, message);
		} catch (JedisConnectionException e) {
			jedisPool.returnBrokenResource(jedis);
			broken = true;
			throw e;
		} finally {
			if (jedis != null && !broken) {
				jedisPool.returnResource(jedis);
			}
		}
	}
}
