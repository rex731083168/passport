package cn.ce.passport.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ce.passport.common.util.JsonUtil;
import cn.ce.passport.common.util.MD5Util;
import cn.ce.passport.common.util.MailInfo;
import cn.ce.passport.common.util.MailUtil;
import cn.ce.passport.dao.persistence.User;
import cn.ce.passport.service.ISessionService;
import cn.ce.passport.service.IUserService;

@RequestMapping("/")
@Controller
public class UserAppController {

	@Resource
	IUserService userService;

	@Resource
	ISessionService sessionService;

	public static Logger logger = LoggerFactory
			.getLogger(UserAppController.class);

	/**
	 * 验证用户名是否存在
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/checkUsername")
	@ResponseBody
	public String checkUsername(
			@RequestParam(required = true, value = "") String username) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");

		Map<String, Object> condition = new HashMap<String, Object>();

		// 图省事,用以后查询用户列表的方法
		condition.put("username", username);
		// condition.put("isvalid", isvalid);

		List<User> userList = userService.getUsers(condition);
		if (userList.size() > 0) {
			retMap.put("code", 201);
			retMap.put("msg", "username has been used!");
		}
		return JsonUtil.toJson(retMap);

	}

	/**
	 * 验证邮箱是否存在
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/checkEmail")
	@ResponseBody
	public String checkEmail(
			@RequestParam(required = true, value = "") String email) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");

		Map<String, Object> condition = new HashMap<String, Object>();

		// 图省事,用以后查询用户列表的方法
		condition.put("email", email);
		// condition.put("isvalid", isvalid);

		List<User> userList = userService.getUsers(condition);
		if (userList.size() > 0) {
			retMap.put("code", 201);
			retMap.put("msg", "email has been used!");
		}
		return JsonUtil.toJson(retMap);

	}

	/**
	 * 发送邮箱code TODO 没写完且没验证
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/sendEmail")
	@ResponseBody
	public String sendEmail(
			@RequestParam(required = true, value = "") String email) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");

		MailInfo mailInfo = new MailInfo();
		mailInfo.setContent("呵呵呵");
		mailInfo.setSubject("哈哈哈");

		mailInfo.setToOne(email);
		MailUtil.send(mailInfo, false);

		return JsonUtil.toJson(retMap);

	}

	/**
	 * 注册 POST
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/register")
	@ResponseBody
	public String register(String username, String email, String password) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "register success!");

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		Date date = new Date();
		user.setCreattime(date);

		user.setStatus(0);
		user.setIsvalid(0);

		// 注册新用户
		int ret = userService.register(user);
		if (ret == 0) {
			retMap.put("code", 201);
			retMap.put("msg", "insert user failed");
		}

		return JsonUtil.toJson(retMap);

	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public String login(HttpServletRequest request,
			@RequestParam(required = true, value = "username") String username,
			@RequestParam(required = true, value = "password") String password) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");
		if (username == null && "".equals(username)) {
			retMap.put("code", 203);
			retMap.put("msg", "parameta error");
		}
		if (password == null && "".equals(password)) {
			retMap.put("code", 203);
			retMap.put("msg", "parameta error");
		}

		String pw = MD5Util.MD5(password);

		User userInfo = userService.getUserInfo(username, pw);
		if (userInfo == null) {
			retMap.put("code", 202);
			retMap.put("msg", "username or password is wrong");
			return JsonUtil.toJson(retMap);
		}
		// 账号被禁用
		if (userInfo.getIsvalid() != 0) {
			retMap.put("code", 202);
			retMap.put("msg", "account is banned");
			return JsonUtil.toJson(retMap);
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("userInfo", userInfo);

		// session TODO redis集群 生成ticket
		String ticket = sessionService.getSession(userInfo.getId());

		dataMap.put("ticket", ticket);
		retMap.put("data", dataMap);
		return JsonUtil.toJson(retMap);

	}

	/**
	 * 根据uid获取用户基本信息
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping("/getUserInfobyUid")
	@ResponseBody
	public String getUserInfobyUid(long uid) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");

		User userInfo = userService.getById(uid);
		retMap.put("data", userInfo);

		return JsonUtil.toJson(retMap);
	}

	/**
	 * 
	 * @param username
	 * @param email
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/updateUser")
	@ResponseBody
	public String updateUser(String userId, String username, String email) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");

		User user = new User();
		if (userId == null && "".equals(userId)) {
			retMap.put("code", 204);
			retMap.put("msg", "parameta error");
		}
		int uid = Integer.parseInt(userId);
		user.setId(uid);
		user.setUsername(username);
		user.setEmail(email);

		// 更新
		int ret = userService.updateUser(user);
		if (ret == 0) {
			retMap.put("code", 201);
			retMap.put("msg", "update user failed");
		}

		// 获取更新完的数据，显示用
		user = userService.getById(uid);

		// 用户表示信息封装
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("id", uid);
		userMap.put("username", user.getUsername());

		userMap.put("email", user.getEmail());
		userMap.put("isvalid", user.getIsvalid());

		Date dateTime = user.getCreattime();
		if (dateTime != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			String dateString = formatter.format(dateTime);
			userMap.put("createtime", dateString);
		} else {
			userMap.put("createtime", null);
		}

		retMap.put("userInfo", userMap);

		return JsonUtil.toJson(retMap);

	}

	/**
	 * 修改密码
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/changePassword")
	@ResponseBody
	public String changePassword(String userId, String oldPassword,
			String newPassword) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "change password success");

		try {
			// 验证旧用户密码
			int uid = Integer.parseInt(userId);
			User userInfo = userService.getById(uid);
			if (userInfo == null || !oldPassword.equals(userInfo.getPassword())) {
				retMap.put("code", 201);
				retMap.put("msg", " oldpassword is wrong");
				return JsonUtil.toJson(retMap);
			}
			// 更新密码
			User user = new User();
			user.setId(userInfo.getId());
			String password = MD5Util.MD5(newPassword);
			user.setPassword(password);
			// 这里应该验证一下更新条数，如果0，说明没更新，不过可以前端拦新旧密码对比
			int ret = userService.updateUser(user);
			// if (ret == 0) {
			// retMap.put("code", 202);
			// retMap.put("msg", "no password updated");
			// return JsonUtil.toJson(retMap);
			// }

		} catch (NumberFormatException e) {
			retMap.put("code", 203);
			retMap.put("msg", "input param error");
			return JsonUtil.toJson(retMap);
		}

		return JsonUtil.toJson(retMap);

	}

	/**
	 * 登出
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public String logout(@RequestParam(required = true, value = "") long uid) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");
		// 清除session
		sessionService.getSession(uid);
		return JsonUtil.toJson(retMap);

	}

	/**
	 * 验证是否已登录
	 * 
	 * @param ticket
	 * @return
	 */
	@RequestMapping(value = "/checkTicket")
	@ResponseBody
	public String checkTicket(HttpServletRequest request,
			@RequestParam(required = true, value = "ticket") String ticket) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");
		if (ticket == null && "".equals(ticket)) {
			retMap.put("code", 203);
			retMap.put("msg", "parameta error");
			return JsonUtil.toJson(retMap);
		}

		// 验证是否过期
		boolean flag = sessionService.checkSession(ticket);

		if (flag == false) {
			retMap.put("code", 202);
			retMap.put("msg", "login time out");
			return JsonUtil.toJson(retMap);
		}

		long uid = sessionService.getUidbyTicket(ticket);
		retMap.put("data", uid);

		return JsonUtil.toJson(retMap);

	}

	// Collections.sort(userList, new Comparator<Map<String, Object>>() {
	//
	// public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	// Long name1 = (Long) o1.get("uid");// name1是从你list里面拿出来的一个
	// Long name2 = (Long) o2.get("uid"); // name1是从你list里面拿出来的第二个name
	// return name1.compareTo(name2);
	// }
	//
	// });

}
