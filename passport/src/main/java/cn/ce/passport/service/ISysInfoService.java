package cn.ce.passport.service;

import java.util.List;

import cn.ce.passport.dao.persistence.SysInfo;


public interface ISysInfoService {
		
	
	//批量查询系统
	List<SysInfo> getSysInfo();

}
