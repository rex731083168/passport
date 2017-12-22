package cn.ce.passport.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ce.passport.common.redis.RedisClusterService;
import cn.ce.passport.common.util.AESUtil;
import cn.ce.passport.common.util.RandomUtil;
import cn.ce.passport.service.ISessionService;

@Service("sessionService")
public class SessionService implements ISessionService {

	@Resource
	RedisClusterService redis;

	@Override
	public String setSession(String uid) {
		// uid 加密 ；存到redis 每次加密生成不同的值，业务上多用户登录同一账号防止互相影响登录状态

		long ran = RandomUtil.random6Number();
		String sessionId = AESUtil.getInstance().encrypt(
				String.valueOf(ran) + uid);
		redis.set(sessionId, "exist", 7 * 24 * 60 * 60);
		return sessionId;

	}

	@Override
	public boolean checkSession(String ticket) {
		String ret = redis.get(ticket);
		if (ret == null || "".equals(ret)) {
			return false;
		}
		return true;

	}

	@Override
	public String getUidbyTicket(String ticket) {
		// 解密出uid
		String sessionId = AESUtil.getInstance().decrypt(ticket);
		if (sessionId == null || "".equals(sessionId)) {
			return null;
		}
		return sessionId.substring(6);

	}

	@Override
	public int delSession(String ticket) {
		// String sessionId = AESUtil.getInstance().encrypt(uid);
		return redis.del(ticket);
//		return ticket;
	}

	@Override
	public String getEmailSession(String email) {
		String emailkey = AESUtil.getInstance().encrypt(email);
		redis.set(emailkey, "exist", 1800);
		return emailkey;
	}

	@Override
	public String getEmail(String emailkey) {
		// 解密出email
		String email = AESUtil.getInstance().decrypt(emailkey);

		return email;
	}

	@Override
	public String setCode(long code) {
		return redis.set(String.valueOf(code), "exist", 3 * 60);
	}

	@Override
	public String getCode(long code) {
		return redis.get(String.valueOf(code));
	}

}
