package cn.ce.passport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ce.passport.dao.mapper.RerationMapper;
import cn.ce.passport.dao.persistence.RRoleAuth;
import cn.ce.passport.dao.persistence.RUserRole;
import cn.ce.passport.service.IRerationService;

@Service("rerationService")
public class RerationServiceImpl implements IRerationService {

	@Resource
	RerationMapper rerationMapper;

	@Override
	public int addRoleAuth(RRoleAuth rRoleAuth) {
		// TODO Auto-generated method stub
		return rerationMapper.addRoleAuth(rRoleAuth);
	}

	@Override
	public int addUserRole(RUserRole rUserRole) {

		return rerationMapper.addUserRole(rUserRole);
	}

	@Override
	public List<RUserRole> getByUId(String uid) {

		return rerationMapper.getByUId(uid);
	}

	@Override
	public int delRoleAuth(long raId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delUserRole(long urId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RRoleAuth> getByRoleId(long roleId) {

		return rerationMapper.getByRoleId(roleId);
	}

	@Override
	public List<RRoleAuth> getByRoleIds(List roleIds) {

		return rerationMapper.getByRoleIds(roleIds);
	}

	@Override
	public int deleteRolebyUid(long uid) {
		return rerationMapper.deleteRolebyUid(uid);
	}

	@Override
	public int deleteAuthbyRoleId(long roleId) {
		return rerationMapper.deleteAuthbyRoleId(roleId);
	}

	@Override
	public List<RUserRole> getUidbyRole(long roleId) {

		return rerationMapper.getUidbyRole(roleId);
	}

}
