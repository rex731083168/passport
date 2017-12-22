package cn.ce.passport.common.util;

import java.util.Random;

/**
 *
 * @author makangwei
 * 2017-8-1
 */
public class RandomUtil {

	private static final Random random = new Random();
	
	/**
	 * 生成16位随机数字符串
	 * @return 
	 */
	public static String random16Number(){
	
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<16; i++){
			int temp = random.nextInt(10);
			sb.append(temp);
		}
		
		return sb.toString();
	}
	
	public static Integer random9Number(){
		
		Integer rm = 0;
		
		for(int i=0; i<8; i++){
			int temp = random.nextInt(10);
			rm = rm*10+temp;
		}
		
		return rm;
	}
	
	public static Integer random6Number(){
		
		Integer rm = 0;
		for(int i=0; i<6; i++){
			int temp = random.nextInt(10);
			rm = rm*10+temp;
		}
		
		return rm;
	}

}
