package cn.ce.passport.service;

import java.util.List;

import cn.ce.passport.dao.persistence.RRoleAuth;
import cn.ce.passport.dao.persistence.RUserRole;

public interface IRerationService {
		
	
	//为角色添加权限
	int addRoleAuth(RRoleAuth rRoleAuth);
	
	//为用户添加角色
	int addUserRole(RUserRole rUserRole);
	
	
	//查询用户角色
	List<RUserRole> getByUId(String uid);
	
	//删除角色的权限
	int delRoleAuth(long raId);
	
	//删除用户的角色
	int delUserRole(long urId);
	
	//删除用户所有角色
	int deleteRolebyUid(long uid);
	
	//删除角色所有权限
	int deleteAuthbyRoleId(long roleId);
	
	//查询角色权限
	List<RRoleAuth> getByRoleId(long roleId);
	
	//查询角色权限 根据系统
	List<RRoleAuth> getByRoleIds(List roleIds);
	
	//查询角色下用户
	List<RUserRole> getUidbyRole(long roleId);
	
 

}
