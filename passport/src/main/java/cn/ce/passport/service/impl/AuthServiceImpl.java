package cn.ce.passport.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ce.passport.dao.mapper.AuthMapper;
import cn.ce.passport.dao.persistence.Auth;
import cn.ce.passport.service.IAuthService;

@Service("authService")
public class AuthServiceImpl implements IAuthService {

	@Resource
	AuthMapper authMapper;

	@Override
	public int addAuth(Auth auth) {
		return authMapper.addAuth(auth);
	}

	@Override
	public int updateAuth(Auth auth) {

		return authMapper.updateAuth(auth);
	}

	@Override
	public Auth getById(long authId) {
		return authMapper.getById(authId);
	}

	@Override
	public List<Auth> getAuths(Map condition) {

		return authMapper.getAuths(condition);
	}

}
