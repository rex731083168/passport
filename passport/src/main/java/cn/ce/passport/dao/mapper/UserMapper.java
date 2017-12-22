package cn.ce.passport.dao.mapper;

import java.util.List;
import java.util.Map;

import cn.ce.passport.dao.persistence.User;

public interface UserMapper {

	public User findById(String uid);

	public int register(User user);

	public List<User> getUsers(Map condition);

	public int updateUser(User user);

	public User getUserInfo(User user);

	public int logout(String uid);

	int queryUserListCountByConditions(Map<String, Object> map);

	List<User> queryUserListByConditions(Map<String, Object> map);

}
