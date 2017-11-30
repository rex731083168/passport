package cn.ce.passport.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ce.passport.dao.mapper.RoleMapper;
import cn.ce.passport.dao.persistence.Role;
import cn.ce.passport.service.IRoleService;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {

	@Resource
	RoleMapper roleMapper;
	
	@Override
	public int addRole(Role role) {
		
		return roleMapper.addRole(role);
	}

	@Override
	public int updateRole(Role role) {
		
		return roleMapper.updateRole(role);
	}

	@Override
	public Role getById(long roleId) {
		return roleMapper.getById(roleId);
	}

	@Override
	public List<Role> getRoles(Map condition) {
		return roleMapper.getRoles(condition);
	}


	

}
