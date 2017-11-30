package cn.ce.passport.dao.mapper;

import java.util.List;
import java.util.Map;

import cn.ce.passport.dao.persistence.Role;

public interface RoleMapper {
		
	//添加角色
	int addRole(Role role);
	
	//更新角色
	int updateRole(Role role);
	
	//查询角色
	Role getById(long roleId);
	
	//批量查询角色
	List<Role> getRoles(Map condition);


}
