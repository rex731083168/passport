package cn.ce.passport.service;


public interface ISessionService {

	// 生成session
	String getSession(long uid);

	// 清除session
	String delSession(long uid);

	// 验证session
	boolean checkSession(String ticket);

	// 根据ticket获取uid
	long getUidbyTicket(String ticket);

}
