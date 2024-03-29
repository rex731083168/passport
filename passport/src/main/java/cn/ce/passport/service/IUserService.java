package cn.ce.passport.service;

import java.util.List;
import java.util.Map;

import cn.ce.passport.dao.persistence.User;

public interface IUserService {

	// 注册
	int register(User user);

	// 查询用户
	User getByUid(String uid);

	// 批量查询
	List<User> getUsers(Map condition);

	// 更新用户状态
	int updateUser(User user);

	// 用户登录 TODO 需定义返回类型
	User getUserInfo(String username, String password);

	// 用户登出
	int logout(String uid);

	// 验证密码
	int checkPassword(String telNumber, String password);

	// 查询用户总量
	int queryUserListCountByConditions(Map<String, Object> map);

	// 查询用户数量
	List<User> queryUserListByConditions(Map<String, Object> map, int pageNo,
			int pageSize);

}
