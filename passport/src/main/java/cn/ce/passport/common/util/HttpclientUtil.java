package cn.ce.passport.common.util;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HttpclientUtil {
    public static String SUCCESS="SUCCESS";
    public static String ERROR="ERROR";
    public static String PARM="PARM";//接口参数
    private static final Logger ILOG = LoggerFactory.getLogger(HttpclientUtil.class);

    /**
     * 接口调用  POST
     * @throws Exception 
     */
    public static String httpURLConnectionPOST (String url,String parm,String type) throws Exception {
    	Map map=new HashMap();
    	map.put(PARM, parm);
        try {
        	if(!type.equals("body"))
        		url=url+parm;
            URL posturl = new URL(url);
            // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
            HttpURLConnection connection = (HttpURLConnection) posturl.openConnection();// 此时cnnection只是为一个连接对象,待连接中
          
            // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            connection.setDoOutput(true);
            
            // 设置连接输入流为true
            connection.setDoInput(true);
            if(type.equals("del")){
            	  // 设置请求方式为post
                connection.setRequestMethod("DELETE");
            }else{
            	  // 设置请求方式为post
                connection.setRequestMethod("POST");
            }
          
            
            // post请求缓存设为false
            connection.setUseCaches(false);
            
            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);
            
            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            // application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();
            if(type.equals("body")){
            	// 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
                DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
//                parm = URLEncoder.encode(parm, "utf-8"); //URLEncoder.encode()方法  为字符串进行编码

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dataout, "UTF-8"));
                System.out.println(parm);
                // 将参数输出到连接
                writer.write(parm);
                // 输出完成后刷新并关闭流
                dataout.flush();
                writer.close();
                dataout.close(); // 重要且易忽略步骤 (关闭流,切记!) 
            }
            System.out.println(connection.getResponseCode());
            connection.disconnect(); // 销毁连接
            if(connection.getResponseCode()==200){
            	  map.put(SUCCESS,connection.getResponseCode());
            }else{
            	  map.put(ERROR,connection.getResponseCode());
            	  ILOG.error("HTTP接口调用失败："+connection.getResponseCode());
            }
          
            return  JsonUtil.toJson(map);
        } catch (Exception e) {
//            e.printStackTrace();
            map.put(ERROR, e.getMessage());
            ILOG.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
    
 }
