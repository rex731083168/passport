package cn.ce.passport.service;

import java.util.List;
import java.util.Map;

import cn.ce.passport.dao.persistence.Auth;

public interface IAuthService {

	// 添加权限
	int addAuth(Auth auth);

	// 更新权限
	int updateAuth(Auth auth);

	// 查询权限
	Auth getById(long authId);

	// 批量查询权限
	List<Auth> getAuths(Map condition);

}
