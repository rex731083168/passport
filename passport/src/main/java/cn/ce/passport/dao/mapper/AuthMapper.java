package cn.ce.passport.dao.mapper;

import java.util.List;
import java.util.Map;

import cn.ce.passport.dao.persistence.Auth;

public interface AuthMapper {

	// 添加权限
	int addAuth(Auth role);

	// 更新权限
	int updateAuth(Auth auth);

	// 查询权限
	Auth getById(long authId);

	// 批量查询权限
	List<Auth> getAuths(Map condition);

}
