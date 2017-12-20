package cn.ce.passport.service;

public interface ISessionService {

	// 生成session
	String setSession(String uid);

	// 清除session
	int delSession(String ticket);

	// 验证session
	boolean checkSession(String ticket);

	// 根据ticket获取uid
	String getUidbyTicket(String ticket);

	// 生成邮件sesion
	String getEmailSession(String email);

	// 根据emailkey获取email
	String getEmail(String emailkey);

	// 缓存code
	String setCode(long code);

	// 获取code
	String getCode(long code);

}
