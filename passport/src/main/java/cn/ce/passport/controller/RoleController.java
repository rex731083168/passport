package cn.ce.passport.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ce.passport.common.util.JsonUtil;
import cn.ce.passport.dao.persistence.RRoleAuth;
import cn.ce.passport.dao.persistence.Role;
import cn.ce.passport.service.IRerationService;
import cn.ce.passport.service.IRoleService;

@RequestMapping("/role")
@Controller
public class RoleController {

	@Resource
	IRoleService roleService;

	@Resource
	IRerationService rerationService;

	public static Logger logger = LoggerFactory.getLogger(RoleController.class);

	@RequestMapping("/getRoles")
	@ResponseBody
	public String getRoles(String roleName, String beganDate, String endDate) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("roleName", roleName);
		condition.put("beganDate", beganDate);
		condition.put("endDate", endDate);
		List<Role> roleList = roleService.getRoles(condition);

		List<Object> resultList = new ArrayList<Object>();

		for (int i = 0; i < roleList.size(); i++) {
			// 角色表示信息封装
			Map<String, Object> roleMap = new HashMap<String, Object>();
			roleMap.put("id", roleList.get(i).getRoleId());

			roleMap.put("roleName", roleList.get(i).getRoleName());

			roleMap.put("Description", roleList.get(i).getDescription());

			Date dateTime = roleList.get(i).getCreateTime();
			if (dateTime != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				String dateString = formatter.format(dateTime);
				roleMap.put("createtime", dateString);
			} else {
				roleMap.put("createtime", null);
			}
			resultList.add(roleMap);

		}
		retMap.put("roleList", resultList);

		return JsonUtil.toJson(retMap);

	}

	@RequestMapping("/addrole")
	@ResponseBody
	public String addrole(String roleName, String description) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");
		Date date = new Date();
		Role role = new Role();
		role.setRoleName(roleName);
		role.setDescription(description);
		role.setCreateTime(date);
		role.setStatus(0);
		int ret = roleService.addRole(role);
		if (ret == 0) {
			retMap.put("code", 200);
			retMap.put("msg", "add role fail");

		}

		// 角色表示信息封装
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("id", role.getRoleId());

		roleMap.put("roleName", role.getRoleName());

		roleMap.put("Description", role.getDescription());

		Date dateTime = role.getCreateTime();
		if (dateTime != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			String dateString = formatter.format(dateTime);
			roleMap.put("createtime", dateString);
		} else {
			roleMap.put("createtime", null);
		}
		retMap.put("role", roleMap);
		return JsonUtil.toJson(retMap);
	}

	@RequestMapping("/getRole")
	@ResponseBody
	public String getRole(String roleId) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		retMap.put("code", 1);
		retMap.put("msg", "ok");
		long roleid = Integer.parseInt(roleId);

		Role role = roleService.getById(roleid);
		dataMap.put("role", role);
		retMap.put("data", dataMap);
		return JsonUtil.toJson(retMap);

	}

	/**
	 * 更新角色
	 * 
	 * 
	 */
	@RequestMapping("/updateRole")
	@ResponseBody
	public String updateRole(long roleId, String roleName, String description) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");

		Role role = new Role();
		role.setRoleId(roleId);
		role.setRoleName(roleName);
		role.setDescription(description);
		int i = roleService.updateRole(role);
		if (i == 0) {
			retMap.put("code", 1);
			retMap.put("msg", "updateFailed");
			return JsonUtil.toJson(retMap);
		}
		// 获取显示对象
		role = roleService.getById(roleId);

		// 角色表示信息封装
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("id", role.getRoleId());

		roleMap.put("roleName", role.getRoleName());

		roleMap.put("Description", role.getDescription());

		Date dateTime = role.getCreateTime();
		if (dateTime != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			String dateString = formatter.format(dateTime);
			roleMap.put("createtime", dateString);
		} else {
			roleMap.put("createtime", null);
		}
		retMap.put("role", roleMap);

		return JsonUtil.toJson(retMap);
	}

	/**
	 * 角色分配权限
	 * 
	 * 
	 */
	@RequestMapping("/allotAuth")
	@ResponseBody
	public String allotAuth(long roleId, String auths) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", 200);
		retMap.put("msg", "ok");
		if (auths == null || "".equals(auths) || roleId == 0) {
			retMap.put("code", 200);
			retMap.put("msg", "parameter error");
		}

		//清除旧的权限
		rerationService.deleteAuthbyRoleId(roleId);
		
		String auth[] = auths.split(",");
		RRoleAuth rroleAuth = new RRoleAuth();
		rroleAuth.setRoleId(roleId);
		for (int i = 0; i < auth.length; i++) {
			rroleAuth.setAuthId(Integer.valueOf(auth[i]));
			rerationService.addRoleAuth(rroleAuth);
		}

		return JsonUtil.toJson(retMap);
	}

}
