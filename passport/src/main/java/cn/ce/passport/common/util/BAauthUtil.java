package cn.ce.passport.common.util;

public class BAauthUtil {

	public static void main(String[] args) {
		BAauthUtil.fBasicAuth(1111111111l);
	}

	public static String fBasicAuth(Long date)

	{

		String res = MD5Util.MD5(date + "E@syW*d");
		// System.out.println(res);
		return res;
	}
}
