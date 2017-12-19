package cn.ce.passport.common.util;

import java.util.UUID;

public class UUIDUtil {
	
	public static void main(String[] args) {
		System.out.print(getUUID());
	}


	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		return uuid.replaceAll("-", "").substring(0,10);
	}
}
