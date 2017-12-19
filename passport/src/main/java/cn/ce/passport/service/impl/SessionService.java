package cn.ce.passport.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ce.passport.common.redis.RedisClusterService;
import cn.ce.passport.common.util.AESUtil;
import cn.ce.passport.service.ISessionService;

@Service("sessionService")
public class SessionService implements ISessionService {

	@Resource
	RedisClusterService redis;

	@Override
	public String setSession(String uid) {
		// uid 加密 ；存到redis
		String sessionId = AESUtil.getInstance().encrypt(uid);
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
		return AESUtil.getInstance().decrypt(ticket);

	}

	@Override
	public String delSession(String uid) {
		String sessionId = AESUtil.getInstance().encrypt(uid);
		redis.del(sessionId);
		return sessionId;
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
