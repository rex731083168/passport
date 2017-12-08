package cn.ce.passport.service;

public interface ISessionService {

	// 生成session
	String setSession(long uid);

	// 清除session
	String delSession(long uid);

	// 验证session
	boolean checkSession(String ticket);

	// 根据ticket获取uid
	long getUidbyTicket(String ticket);

	// 生成邮件sesion
	String getEmailSession(String email);

	// 根据emailkey获取email
	String getEmail(String emailkey);

	// 缓存code
	String setCode(long code);

	// 获取code
	String getCode(String code);

}
