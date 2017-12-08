package cn.ce.passport.controller;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ce.passport.common.util.JsonUtil;
import cn.ce.passport.common.util.MailInfo;
import cn.ce.passport.common.util.MailUtil;
import cn.ce.passport.common.util.RandomUtil;
import cn.ce.passport.dao.persistence.RUserRole;
import cn.ce.passport.dao.persistence.Role;
import cn.ce.passport.dao.persistence.User;
import cn.ce.passport.service.IRerationService;
import cn.ce.passport.service.IRoleService;
import cn.ce.passport.service.ISessionService;
import cn.ce.passport.service.IUserService;
import cn.ce.platform_service.common.ErrorCodeNo;

import com.google.gson.Gson;

@RequestMapping("/")
@Controller
public class UserAppController {

	@Resource
	IUserService userService;

	@Resource
	ISessionService sessionService;

	@Resource
	IRerationService rerationService;

	@Resource
	IRoleService roleService;

	@Value("${send.url}")
	private String url;

	public static Logger logger = LoggerFactory
			.getLogger(UserAppController.class);

	/**
	 * 验证用户名是否存在
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/checkUserName")
	@ResponseBody
	public String checkUserName(
			@RequestParam(required = true, value = "") String userName) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		logger.info("userName", userName);
		Map<String, Object> condition = new HashMap<String, Object>();

		// 图省事,用以后查询用户列表的方法
		condition.put("userName", userName);
		// condition.put("isvalid", isvalid);

		List<User> userList = userService.getUsers(condition);
		if (userList.size() > 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS009);
			retMap.put("msg", ErrorCodeNo.SYS009.getDesc());

		}
		return JsonUtil.toJson(retMap);

	}

	/**
	 * 验证身份证是否存在
	 * 
	 * @param idCard
	 * @return
	 */
	@RequestMapping(value = "/checkIdCard")
	@ResponseBody
	public String checkIdCard(
			@RequestParam(required = true, value = "") String idCard) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		logger.info("idCard", idCard);
		Map<String, Object> condition = new HashMap<String, Object>();

		// 图省事,用以后查询用户列表的方法
		condition.put("idCard", idCard);

		List<User> userList = userService.getUsers(condition);
		if (userList.size() > 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS009);
			retMap.put("msg", ErrorCodeNo.SYS009.getDesc());

		}
		return JsonUtil.toJson(retMap);

	}

	/**
	 * 验证邮箱是否存在
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/checkEmail")
	@ResponseBody
	public String checkEmail(
			@RequestParam(required = true, value = "") String email) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		Map<String, Object> condition = new HashMap<String, Object>();

		// 图省事,用以后查询用户列表的方法
		condition.put("email", email);
		// condition.put("isvalid", isvalid);

		List<User> userList = userService.getUsers(condition);
		if (userList.size() > 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS009);
			retMap.put("msg", ErrorCodeNo.SYS009.getDesc());

		}
		return JsonUtil.toJson(retMap);

	}

	/**
	 * 注册发送邮箱code TODO 没写完且没验证
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/sendEmail")
	@ResponseBody
	public String sendEmail(
			@RequestParam(required = true, value = "") String email) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		long code = RandomUtil.random6Number();
		MailInfo mailInfo = new MailInfo();
		mailInfo.setToOne(email);
		mailInfo.setSubject("平台注册验证码");
		mailInfo.setContent("验证码为：" + code + ";有效期为3分钟");

		// TODO code存到session里
		sessionService.setCode(code);

		MailUtil.send(mailInfo, true);

		return JsonUtil.toJson(retMap);

	}

	/**
	 * 注册 POST
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/register")
	@ResponseBody
	public String register( @RequestBody User userInfo) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

	//	User user = new Gson().fromJson(userInfo, User.class);

		Date date = new Date();
		userInfo.setRegTime(date);
		// user.setPassword(MD5Util.MD5(user.getPassword()));
		userInfo.setState(1);
		userInfo.setCheckState(0);

		// 注册新用户
		int ret = userService.register(userInfo);
		if (ret == 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS001);
			retMap.put("msg", ErrorCodeNo.SYS001.getDesc());
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
			@RequestParam(required = true, value = "userName") String userName,
			@RequestParam(required = true, value = "password") String password,
			@RequestParam(required = true, value = "sysId") String sysId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());
		if (userName == null && "".equals(userName)) {
			retMap.put("errorCode", ErrorCodeNo.SYS005);
			retMap.put("msg", ErrorCodeNo.SYS005.getDesc());
		}
		if (password == null && "".equals(password)) {
			retMap.put("errorCode", ErrorCodeNo.SYS005);
			retMap.put("msg", ErrorCodeNo.SYS005.getDesc());
		}

		int sid = Integer.valueOf(sysId);
		// String pw = MD5Util.MD5(password);

		User userInfo = userService.getUserInfo(userName, password);
		if (userInfo == null) {

			retMap.put("errorCode", ErrorCodeNo.SYS022);
			retMap.put("msg", ErrorCodeNo.SYS022.getDesc());

			return JsonUtil.toJson(retMap);
		}
		// 账号被禁用
		if (userInfo.getState() == 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS023);
			retMap.put("msg", ErrorCodeNo.SYS023.getDesc());

			return JsonUtil.toJson(retMap);
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();

		List<Object> roleList = new ArrayList<>();
		dataMap.put("userInfo", userInfo);

		// 获取用户角色 本期权限先不搞，只确认角色 没有角色，可以先不让登录
		// 后续根据获取的角色，关联权限，拿到权限直接展开页面。
		List<RUserRole> userRole = rerationService.getByUId(userInfo.getId());
		if (userRole == null) {
			retMap.put("errorCode", ErrorCodeNo.SYS013);
			retMap.put("msg", ErrorCodeNo.SYS013.getDesc());
			return JsonUtil.toJson(retMap);
		}

		for (int i = 0; i < userRole.size(); i++) {
			Role role = roleService.getById(userRole.get(i).getRoleId());
			// 是本系统的才放到list
			if (sid == role.getBelongSys()) {
				roleList.add(role);
			}

		}

		// session
		String ticket = sessionService.setSession(userInfo.getId());

		dataMap.put("ticket", ticket);
		dataMap.put("role", roleList);
		retMap.put("data", dataMap);
		return JsonUtil.toJson(retMap);

	}

	/**
	 * 
	 * @param userName
	 * @param email
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/updateUser")
	@ResponseBody
	public String updateUser(String userInfo) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		User user = new Gson().fromJson(userInfo, User.class);

		// 更新
		int ret = userService.updateUser(user);
		if (ret == 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS004);
			retMap.put("msg", ErrorCodeNo.SYS004.getDesc());
			return JsonUtil.toJson(retMap);
		}

		// 获取更新完的数据，显示用
		user = userService.getById(user.getId());

		if (ret == 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS006);
			retMap.put("msg", ErrorCodeNo.SYS006.getDesc());
			return JsonUtil.toJson(retMap);
		}
		// 用户表示信息封装
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("id", user.getId());
		userMap.put("userName", user.getUserName());

		userMap.put("email", user.getEmail());
		userMap.put("status", user.getState());

		Date dateTime = user.getRegTime();
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
	 * 
	 */
	@RequestMapping(value = "/changePassword")
	@ResponseBody
	public String changePassword(String userId, String oldPassword,
			String newPassword) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		try {
			// 验证旧用户密码
			int uid = Integer.parseInt(userId);
			User userInfo = userService.getById(uid);
			if (userInfo == null || !oldPassword.equals(userInfo.getPassword())) {
				retMap.put("errorCode", ErrorCodeNo.SYS022);
				retMap.put("msg", ErrorCodeNo.SYS022.getDesc());
				return JsonUtil.toJson(retMap);
			}
			// 更新密码
			User user = new User();
			user.setId(userInfo.getId());
			// String password = MD5Util.MD5(newPassword);
			user.setPassword(newPassword);
			// 这里应该验证一下更新条数，如果0，说明没更新，不过可以前端拦新旧密码对比
			int ret = userService.updateUser(user);
			// if (ret == 0) {
			// retMap.put("errorCode", 202);
			// retMap.put("msg", "no password updated");
			// return JsonUtil.toJson(retMap);
			// }

		} catch (NumberFormatException e) {
			retMap.put("errorCode", ErrorCodeNo.SYS008);
			retMap.put("msg", ErrorCodeNo.SYS008.getDesc());
			return JsonUtil.toJson(retMap);
		}

		return JsonUtil.toJson(retMap);

	}

	/**
	 * 发送邮箱验证邮件
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/sendMailforPassword")
	@ResponseBody
	public String sendMailforPassword(
			@RequestParam(required = true, value = "") String email, String path) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		// int uid = Integer.valueOf(userId);
		MailInfo mailInfo = new MailInfo();
		mailInfo.setToOne(email);
		mailInfo.setSubject("重置密码");
		mailInfo.setContent("点链接：" + url + path + "?code="
				+ URLEncoder.encode(sessionService.getEmailSession(email)));

		MailUtil.send(mailInfo, true);

		return JsonUtil.toJson(retMap);

	}

	/**
	 * 找回密码
	 * 
	 * 
	 */
	@RequestMapping(value = "/resetPassword")
	@ResponseBody
	public String resetPassword(String code, String password)
			throws BadPaddingException {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		try {
			String email = sessionService.getEmail(code);

			Map<String, Object> condition = new HashMap<String, Object>();
			// 图省事,用以后查询用户列表的方法
			condition.put("email", email);

			List<User> userList = userService.getUsers(condition);

			if (userList.size() == 0) {
				retMap.put("errorCode", 201);
				retMap.put("msg", "Invalid email !");
				return JsonUtil.toJson(retMap);
			}

			// 更新密码
			User user = userList.get(0);
			// String newpassword = MD5Util.MD5(password);
			user.setPassword(password);
			// 这里应该验证一下更新条数，如果0，说明没更新
			int ret = userService.updateUser(user);
			// if (ret == 0) {
			// retMap.put("errorCode", 202);
			// retMap.put("msg", "no password updated");
			// return JsonUtil.toJson(retMap);
			// }

		} catch (NumberFormatException e) {
			retMap.put("errorCode", ErrorCodeNo.SYS008);
			retMap.put("msg", ErrorCodeNo.SYS008.getDesc());
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
	public String logout(@RequestParam(required = true, value = "") long userId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		// 清除session
		sessionService.delSession(userId);
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
	public String checkTicket(HttpServletRequest request) {
		// public String checkTicket(HttpServletRequest request,
		// @RequestParam(required = true, value = "ticket") String ticket) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		String ticket = request.getHeader("ticket");
		if (ticket == null && "".equals(ticket)) {
			retMap.put("errorCode", ErrorCodeNo.SYS005);
			retMap.put("msg", ErrorCodeNo.SYS005.getDesc());

			return JsonUtil.toJson(retMap);
		}

		// 验证是否过期
		boolean flag = sessionService.checkSession(ticket);

		if (flag == false) {
			retMap.put("errorCode", ErrorCodeNo.SYS019);
			retMap.put("msg", ErrorCodeNo.SYS019.getDesc());

			return JsonUtil.toJson(retMap);
		}

		long uid = sessionService.getUidbyTicket(ticket);
		User userInfo = userService.getById(uid);
		retMap.put("data", userInfo);
		// retMap.put("data", uid);

		return JsonUtil.toJson(retMap);

	}

	// /**
	// * 根据uid获取用户基本信息 可以暂时不用
	// *
	// * @param uid
	// * @return
	// */
	// @RequestMapping("/getUserInfobyUid")
	// @ResponseBody
	// public String getUserInfobyUid(long uid) {
	//
	// Map<String, Object> retMap = new HashMap<String, Object>();
	//
	// User userInfo = userService.getById(uid);
	// retMap.put("data", userInfo);
	//
	// return JsonUtil.toJson(retMap);
	// }

	/**
	 * 禁用启用用户
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping("/modUserState")
	@ResponseBody
	public String modUserState(long userId, int state) {

		Map<String, Object> retMap = new HashMap<String, Object>();

		User user = new User();
		user.setId(userId);
		user.setState(state);
		int ret = userService.updateUser(user);
		if (ret == 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS004);
			retMap.put("msg", ErrorCodeNo.SYS004.getDesc());
			return JsonUtil.toJson(retMap);
		}

		return JsonUtil.toJson(retMap);
	}

	@RequestMapping(value = "/getUserList")
	@ResponseBody
	public String getUserList(String userName, int userType, int checkState,
			int state, String email, int currentPage, int pageSize) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());
		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("userName", userName);
		condition.put("userType", userType);
		condition.put("checkState", checkState);
		condition.put("state", state);
		condition.put("email", email);

		int totalCnt = userService.queryUserListCountByConditions(condition);
		List<User> userList = userService.queryUserListByConditions(condition,
				currentPage, pageSize);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("userList", userList);
		dataMap.put("cnt", totalCnt);

		retMap.put("data", dataMap);
		return JsonUtil.toJson(retMap);

	}

}
