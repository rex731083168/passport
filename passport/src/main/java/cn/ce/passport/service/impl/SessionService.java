package cn.ce.passport.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ce.passport.common.redis.RedisService;
import cn.ce.passport.common.util.AESUtil;
import cn.ce.passport.service.ISessionService;;

@Service("sessionService")
public class SessionService implements ISessionService {

	@Resource
	RedisService redis;

	@Override
	public String getSession(long uid) {
		// uid 加密 ；存到redis
		String sessionId = AESUtil.getInstance().encrypt(String.valueOf(uid));
		redis.set(sessionId, "exist", 7 * 24 * 60 * 60);
		return sessionId;
		// return null;
	}

	@Override
	public boolean checkSession(String ticket) {
		String ret = redis.get(ticket);
		if (ret == null || "".equals(ret)) {
			return false;
		}
		return true;
		// return true;

	}

	@Override
	public long getUidbyTicket(String ticket) {
		// 解密出uid
		String uidStr = AESUtil.getInstance().decrypt(ticket);
		long uid = Long.valueOf(uidStr);
		return uid;
		// return 0;
	}

	@Override
	public String delSession(long uid) {
		String sessionId = AESUtil.getInstance().encrypt(String.valueOf(uid));
		redis.del(sessionId);
		return sessionId;
	}

}
