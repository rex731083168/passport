package cn.ce.passport.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ce.passport.dao.mapper.UserMapper;
import cn.ce.passport.dao.persistence.User;
import cn.ce.passport.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource
	UserMapper userMapper;

	@Override
	public User getById(long uid) {
		return userMapper.findById(uid);
	}

	@Override
	public int register(User user) {

		return userMapper.register(user);
	}

	@Override
	public List<User> getUsers(Map condition) {

		return userMapper.getUsers(condition);
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUser(user);
	}

	@Override
	public User getUserInfo(String userName, String password) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		return userMapper.getUserInfo(user);
	}

	@Override
	public int logout(long uid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkPassword(String telNumber, String password) {
		User user = new User();
		user.setTelNumber(telNumber);
		user.setPassword(password);
		User res = new User();
		res = userMapper.getUserInfo(user);
		if (res == null) {
			return 0;
		} else
			return 1;
	}

	@Override
	public int queryUserListCountByConditions(Map<String, Object> map) {
		return userMapper.queryUserListCountByConditions(map);
	}

	@Override
	public List<User> queryUserListByConditions(Map<String, Object> map,
			int pageNo, int pageSize) {

		map.put("begain", (pageNo - 1) * pageSize);
		map.put("pageSize", pageSize);
		return userMapper.queryUserListByConditions(map);
	}

}
