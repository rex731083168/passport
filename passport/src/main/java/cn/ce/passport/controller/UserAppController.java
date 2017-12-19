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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ce.passport.common.ErrorCodeNo;
import cn.ce.passport.common.util.JsonUtil;
import cn.ce.passport.common.util.MailInfo;
import cn.ce.passport.common.util.MailUtil;
import cn.ce.passport.common.util.RandomUtil;
import cn.ce.passport.common.util.UUIDUtil;
import cn.ce.passport.dao.persistence.RUserRole;
import cn.ce.passport.dao.persistence.Role;
import cn.ce.passport.dao.persistence.User;
import cn.ce.passport.service.IRerationService;
import cn.ce.passport.service.IRoleService;
import cn.ce.passport.service.ISessionService;
import cn.ce.passport.service.IUserService;

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
	 * 注册发送邮箱code
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

		logger.info("code:", code);

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
	public String register(@RequestParam String userInfo,
			@RequestParam(required = true, value = "sysId") int sysId,
			@RequestParam(required = true, value = "code") int code) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		String isExist = sessionService.getCode(code);
		if ("".equals(isExist)) {
			retMap.put("errorCode", ErrorCodeNo.SYS011);
			retMap.put("msg", ErrorCodeNo.SYS011.getDesc());
			return JsonUtil.toJson(retMap);
		}
		 User user = new Gson().fromJson(userInfo, User.class);
		//User user = userInfo;

		String uid = UUIDUtil.getUUID();
		Date date = new Date();
		user.setUid(uid);
		user.setRegTime(date);
		user.setState(1);
		user.setCheckState(0);
		// 开放平台 TODO
		// if (sysId == 101) {
		// user.setUserType(1);
		// }
		user.setUserType(1);

		// 注册新用户
		int ret = userService.register(user);
		if (ret == 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS001);
			retMap.put("msg", ErrorCodeNo.SYS001.getDesc());

		}
		// 添加用户权限
		// 根据sysInfo获取用户角色
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("belongSys", sysId);
		List<Role> roles = roleService.getRoles(condition);
		// 将角色赋予用户
		for (int i = 0; i < roles.size(); i++) {
			RUserRole rUserRole = new RUserRole();
			rUserRole.setRoleId(roles.get(i).getRoleId());
			rUserRole.setUid(uid);
			rerationService.addUserRole(rUserRole);
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
		// 密码不显示给前端
		userInfo.setPassword("");
		dataMap.put("userInfo", userInfo);

		// 获取用户角色 本期权限先不搞，只确认角色 没有角色，可以先不让登录
		// 后续根据获取的角色，关联权限，拿到权限直接展开页面。
		List<RUserRole> userRole = rerationService.getByUId(userInfo.getUid());
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
		String ticket = sessionService.setSession(userInfo.getUid());

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
		String uid = user.getUid();
		// 更新
		int ret = userService.updateUser(user);
		if (ret == 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS004);
			retMap.put("msg", ErrorCodeNo.SYS004.getDesc());
			return JsonUtil.toJson(retMap);
		}

		// 获取更新完的数据，显示用
		user = userService.getByUid(uid);

		if (ret == 0) {
			retMap.put("errorCode", ErrorCodeNo.SYS006);
			retMap.put("msg", ErrorCodeNo.SYS006.getDesc());
			return JsonUtil.toJson(retMap);
		}
		// 用户表示信息封装
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("uid", user.getUid());
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
			User userInfo = userService.getByUid(userId);
			if (userInfo == null || !oldPassword.equals(userInfo.getPassword())) {
				retMap.put("errorCode", ErrorCodeNo.SYS022);
				retMap.put("msg", ErrorCodeNo.SYS022.getDesc());
				return JsonUtil.toJson(retMap);
			}
			// 更新密码
			User user = new User();
			user.setUid(userId);
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
		mailInfo.setContent("点链接：" + url + path + "&code="
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
				retMap.put("errorCode", ErrorCodeNo.SYS006);
				retMap.put("msg", ErrorCodeNo.SYS006.getDesc());
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
	public String logout(
			@RequestParam(required = true, value = "") String userId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());

		// 清除session
		// TODO 告诉前端
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

		String uid = sessionService.getUidbyTicket(ticket);
		User userInfo = userService.getByUid(uid);
		retMap.put("data", userInfo);

		return JsonUtil.toJson(retMap);

	}

	/**
	 * 禁用启用用户
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping("/modUserState")
	@ResponseBody
	public String modUserState(String userId, int state) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());
		User user = new User();
		user.setUid(userId);
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
	public String getUserList(
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "userType", required = false) Integer userType,
			@RequestParam(value = "checkState", required = false) Integer checkState,
			@RequestParam(value = "state", required = false) Integer state,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {

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

	@RequestMapping(value = "/auditUser")
	@ResponseBody
	public String auditUser(String checkMem, int checkState, String userIds) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("errorCode", ErrorCodeNo.SYS000);
		retMap.put("msg", ErrorCodeNo.SYS000.getDesc());
		int count = 0;
		String[] ids = userIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			User user = new User();
			user.setUid(ids[i]);
			user.setCheckMem(checkMem);
			user.setCheckState(checkState);
			int ret = userService.updateUser(user);
			if (ret > 0) {
				count++;
			}
		}
		if (count < ids.length) {
			retMap.put("errorCode", ErrorCodeNo.SYS027);
			retMap.put("msg", ErrorCodeNo.SYS027.getDesc());
			return JsonUtil.toJson(retMap);
		}

		return JsonUtil.toJson(retMap);

	}

}
