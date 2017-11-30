package cn.ce.passport.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ce.passport.common.util.JsonUtil;
import cn.ce.passport.dao.persistence.Auth;
import cn.ce.passport.dao.persistence.SysInfo;
import cn.ce.passport.service.IAuthService;
import cn.ce.passport.service.IRerationService;
import cn.ce.passport.service.ISysInfoService;
import cn.ce.passport.service.IUserService;

@RequestMapping("/auth")
@Controller
public class AuthController {

	@Resource
	IUserService userService;

	@Resource
	IAuthService authService;

	@Resource
	ISysInfoService sysInfoService;

	@Resource
	IRerationService rerationService;

	public static Logger logger = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping("/getAuths")
	@ResponseBody
	public String getAuths(String authName, String systemId, String isvalid) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("authName", authName);
		condition.put("systemId", systemId);
		condition.put("isvalid", isvalid);

		List<Auth> authList = authService.getAuths(condition);
		retMap.put("authList", authList);

		return JsonUtil.toJson(retMap);

	}

	@RequestMapping("/addAuth")
	@ResponseBody
	public String addAuth(String authName, String url, String systemId,
			long parentId) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");
		if (systemId == null || "".equals(systemId)) {
			retMap.put("code", 1);
			retMap.put("msg", "systemId is required");
		}
		int belongSys = Integer.valueOf(systemId);
		Date date = new Date();
		Auth auth = new Auth();
		auth.setAuthName(authName);
		auth.setUrl(url);
		auth.setBelongSys(belongSys);
		auth.setCreateTime(date);
		auth.setParentId(parentId);
		auth.setIsvalid(0);
		auth.setStatus(0);
		int ret = authService.addAuth(auth);
		if (ret == 0) {
			retMap.put("code", 200);
			retMap.put("msg", "insert auth failed");
			return JsonUtil.toJson(retMap);
		}

		retMap.put("auth", auth);
		return JsonUtil.toJson(retMap);

	}

	/**
	 * 获取权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAuth")
	@ResponseBody
	public String getAuth(HttpServletRequest request, String authId) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");
		long authid = Integer.parseInt(authId);

		Auth auth = authService.getById(authid);
		List<SysInfo> sysInfo = sysInfoService.getSysInfo();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("sysInfo", sysInfo);
		dataMap.put("auth", auth);

		retMap.put("data", dataMap);
		return JsonUtil.toJson(retMap);

	}

	@RequestMapping("/updateAuth")
	@ResponseBody
	public String updateAuth(long authId, String authName, String url,
			String systemId, long parentId) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");
		int belongSys = Integer.valueOf(systemId);
		Auth auth = new Auth();
		auth.setAuthId(authId);
		auth.setAuthName(authName);
		auth.setUrl(url);
		auth.setBelongSys(belongSys);
		auth.setParentId(parentId);
		int i = authService.updateAuth(auth);
		if (i == 0) {
			retMap.put("code", 1);
			retMap.put("msg", "updateFailed");
			return JsonUtil.toJson(retMap);
		}

		retMap.put("auth", auth);
		return JsonUtil.toJson(retMap);

	}

}
