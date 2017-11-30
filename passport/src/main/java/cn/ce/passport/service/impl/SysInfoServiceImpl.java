package cn.ce.passport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ce.passport.dao.mapper.SysInfoMapper;
import cn.ce.passport.dao.persistence.SysInfo;
import cn.ce.passport.service.ISysInfoService;

@Service("sysInfoService")
public class SysInfoServiceImpl implements ISysInfoService {

	@Resource
	SysInfoMapper sysInfoMapper;

	@Override
	public List<SysInfo> getSysInfo() {
		return sysInfoMapper.getSysInfo();
	}
	




	

}
