package cn.ce.passport.dao.mapper;


import java.util.List;

import cn.ce.passport.dao.persistence.RRoleAuth;
import cn.ce.passport.dao.persistence.RUserRole;

public interface RerationMapper {

	int addRoleAuth(RRoleAuth rRoleAuth);
	
	int addUserRole(RUserRole rUserRole);
	
	List<RUserRole> getByUId(long uid);
	
	int delRoleAuth(long raId);
	
	int delUserRole(long urId);


	int deleteRolebyUid(long uid);
	
	int deleteAuthbyRoleId(long roleId);
	
	List<RRoleAuth> getByRoleId(long roleId);
	
	List<RRoleAuth> getByRoleIds(List roleIds);

	List<RUserRole> getUidbyRole(long roleId);
	
}
