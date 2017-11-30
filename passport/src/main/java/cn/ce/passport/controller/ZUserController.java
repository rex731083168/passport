package cn.ce.passport.controller;
//package cn.easywed.stargate.controller;
//
//
//
//import java.util.UUID;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import cn.ce.platform_service.common.Constants;
//import cn.ce.platform_service.common.ErrorCodeNo;
//import cn.ce.platform_service.common.Result;
//import cn.ce.platform_service.common.Status;
//import cn.ce.platform_service.users.entity.User;
//import cn.ce.platform_service.users.service.IConsoleUserService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//
///**
// * @ClassName:  UserController   
// * @Description:Console用户控制类
// * @author: makangwei
// * @date:   2017年10月11日 下午2:27:28   
// * @Copyright: 2017 中企动力科技股份有限公司 © 1999-2017 300.cn All Rights Reserved
// */
//@RestController
//@RequestMapping("/user")
//@Api("用户管理")
//public class UserController {
//
//	/** 日志对象 */
//	private static Logger _LOGGER = Logger.getLogger(UserController.class);
//
//	@Resource
//	private IConsoleUserService consoleUserService;
//
//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	@ApiOperation("用户注册")
//	public Result<?> userRegister(HttpServletRequest request, HttpServletResponse response, HttpSession session,
//			@RequestBody User user) {
//		
//		_LOGGER.info("userName:"+user.getUserName());
//		_LOGGER.info("email:"+user.getEmail());
//		
//		
//		_LOGGER.info("校验验证码是否正确");
//		Integer checkCode1 = (Integer)session.getAttribute(user.getEmail());
//		
//		
//		//邮箱验证码校验
//		Result<String> result = new Result<String>();
//		
//		if(checkCode1 == null){
//			result.setErrorMessage("session数据不存在", ErrorCodeNo.SYS019);
//			return result;
//		}
//		if(!user.getCheckCode().equals(checkCode1)){
//			result.setErrorMessage("验证码错误", ErrorCodeNo.SYS008);
//			return result;
//		}
//		 
//		return consoleUserService.userRegister(user);
//	}
//
//	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
//	@ApiOperation("用户登陆后认证信息")
//	public Result<?> userAuthenticate(
//			HttpSession session,
//			@RequestParam String userId,
//			@RequestParam String enterpriseName, //企业名称 
//			@RequestParam String idCard, //用户身份证号码
//			@RequestParam String userRealName //用户真实姓名
//			){
//			
//		return consoleUserService.authenticate(userId,enterpriseName,idCard,userRealName,session);
//	}
//	
//	@RequestMapping(value="/checkIdCard",method=RequestMethod.GET)
//	@ApiOperation("校验身份证唯一性")
//	public Result<?> checkIdCard(String idCard){
//		
//		if(StringUtils.isBlank(idCard)){
//			return Result.errorResult("idCard不能为空", ErrorCodeNo.SYS005, null, Status.FAILED);
//		}
//		
//		return consoleUserService.checkIdCard(idCard);
//	}
//	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	@ApiOperation("登录")
//	public Result<?> login(HttpSession session, 
//			@RequestParam String userName, 
//			@RequestParam String password) {
//
//		return consoleUserService.login(session, userName,password);
//	}
//	
//	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
//	@ApiOperation("检查登录")
//	public Result<?> checkLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		
//		Result<User> result = new Result<User>();
//		User user = null;
//		
//		
//
//		Object userObj = session.getAttribute(Constants.SES_LOGIN_USER);
//		if(userObj == null){
//			return Result.errorResult("session已过期", ErrorCodeNo.SYS019, null, Status.FAILED);
//		}
//		
//		try{
//			user = (User) session.getAttribute(Constants.SES_LOGIN_USER);
//		}catch(Exception e){
//			_LOGGER.error("error happens when get user info from session",e);
//			return Result.errorResult("session已过期", ErrorCodeNo.SYS019, null, Status.FAILED);
//		}
//		
//		_LOGGER.info("*********checkLogin 当前session仍然存在user数据");
//		User userNew = consoleUserService.findUserById(user.getId());
//		
//		if(userNew != null){
//			userNew.setPassword("");
//		}
//		_LOGGER.info("********checkLogin 获取新的用户数据：");
//		session.setAttribute(Constants.SES_LOGIN_USER, userNew);
//		
//		result.setSuccessData(userNew);
//		
//		return result;
//	}
//
//	@RequestMapping(value = "/logOut", method = RequestMethod.POST)
//	@ApiOperation("退出登录")
//	public Result<?> logOut(HttpSession session) {
//		_LOGGER.info("---------->> Action for logout");
//		Result<String> result = new Result<String>();
//		try {
//			session.invalidate();
//			result.setSuccessMessage("");
//			return result;
//		} catch (Exception e) {
//			_LOGGER.info("error happens when execute user logout",e);
//			result.setErrorMessage("");
//			return result;
//		}
//	}
//	
//	@RequestMapping(value="modifyPassword",method=RequestMethod.POST)
//	@ApiOperation("忘记密码后重置密码(需先经过邮箱验证)")
//	public Result<?> modifyPassword(HttpSession session,
//			@RequestParam String email,
//			@RequestParam String newPassword,
//			@RequestParam String uuid){
//		
//		String uuid1 = (String)session.getAttribute("uuid");
//		if(!uuid.equals(uuid1)){
//			Result<String> result = new Result<String>();
//			result.setErrorMessage("uuid错误",ErrorCodeNo.SYS016);
//			return result;
//		}
//		
//		return consoleUserService.modifyPassword(email,newPassword);
//	}
//	
//	@RequestMapping(value="/sendRegistEmail", method=RequestMethod.POST)
//	@ApiOperation("注册时发送邮箱验证码")
//	public Result<?> sendRegistEmail(HttpSession session, @RequestParam String email){
//		
//		return consoleUserService.sendRegistEmail(email,session);
//	}
//	
//	@RequestMapping(value="/sendRePwdEmail", method=RequestMethod.POST)
//	@ApiOperation("忘记密码时发送邮箱验证码")
//	public Result<?> sendRePwdEmail(HttpSession session, @RequestParam String email){
//		
//		return consoleUserService.sendRePwdEmail(email,session);
//	}
//	
//	@RequestMapping(value="checkTelVerifyCode",method=RequestMethod.POST)
//	@ApiOperation("校验忘记密码时发送的验证码")
//	public Result<?> checkTelVerifyCode(HttpSession session,
//			@RequestParam String email,
//			@RequestParam String telVerifyCode){
//		
//		String verifyCode = session.getAttribute(email) == null ? "" : session.getAttribute(email).toString();
//		Long transTime = (Long) session.getAttribute(email+"TransTime");
//		
//		Result<String> result = new Result<String>();
//		if(StringUtils.isBlank(verifyCode) || transTime == null){
//			result.setErrorMessage("当前是新的session");
//			return result;
//		}
//		if(!telVerifyCode.equals(verifyCode)){
//			result.setErrorMessage("验证码错误",ErrorCodeNo.SYS008);
//			return result;
//		}
//		if((transTime + Constants.TEL_VALIDITY) < System.currentTimeMillis()){
//			result.setErrorMessage("验证码过期",ErrorCodeNo.SYS011);
//			return result;
//		}
//		
//		String uuid = UUID.randomUUID().toString();
//		session.setAttribute("uuid", uuid);
//		result.setSuccessData(uuid);
//		return result;
//	}
//	
//	//校验邮箱
//	@RequestMapping(value="/checkEmail",method=RequestMethod.GET)
//	@ApiOperation("校验邮箱是否可用")
//	public Result<?> checkEmail(HttpServletRequest request,HttpServletResponse response,
//			String email){
//	
//		if(StringUtils.isBlank(email)){
//			Result<String> result = new Result<String>();
//			result.setErrorMessage("邮箱不能为空",ErrorCodeNo.SYS005);
//			return result;
//		}
//		return consoleUserService.checkEmail(email);
//	}
//	
//	@RequestMapping(value="/checkUserName",method=RequestMethod.GET)
//	@ApiOperation("校验用户名是否可用")
//	public Result<?> checkUserName(String userName){
//		
//		if(StringUtils.isBlank(userName)){
//			Result<String> result = new Result<String>();
//			result.setErrorMessage("当前用户名不能为空",ErrorCodeNo.SYS005);
//			return result;
//		}
//		
//		return consoleUserService.checkUserName(userName);
//	}
//	
//	@RequestMapping(value="/checkTelNumber",method=RequestMethod.GET)
//	@ApiOperation("校验手机号是否可用")
//	public Result<?> checkTelNumber(String telNumber){
//		
//		if(StringUtils.isBlank(telNumber)){
//			Result<String> result = new Result<String>();
//			result.setErrorMessage("手机号不能为空",ErrorCodeNo.SYS005);
//			return result;
//		}
//		
//		return consoleUserService.checkTelNumber(telNumber);
//	}
//	
//}
