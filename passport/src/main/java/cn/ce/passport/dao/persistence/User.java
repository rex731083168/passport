package cn.ce.passport.dao.persistence;

import java.util.Date;

public class User {

	private long id;
	private String userName;
	private String password;
	private String email;
	private String telNumber;
	private Date regTime;
	/** 组织机构编码,标识当前用户的组织 */
	private String orgId;
	/** 组织机构名称 冗余字段 */
	private String orgName;
	/** 状态 0:禁用，1:启用 */
	private int state;
	/** 用户类型 0:管理员，1:普通用户，2:提供者 */
	private int userType;
	/** 审核状态0:初始，1:提交审核，2:通过，3:未通过 */
	private int checkState;
	/** 审核备注 */
	private String checkMem;
	/******************** 认证时需要的信息 ************************/
	/** 企业名称 */
	private String enterpriseName; //
	/** 身份证号码 */
	private String idCard;
	/** 真实姓名 */
	private String userRealName;

	/******************** 认证时需要的信息 ************************/
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getCheckState() {
		return checkState;
	}

	public void setCheckState(int checkState) {
		this.checkState = checkState;
	}

	public String getCheckMem() {
		return checkMem;
	}

	public void setCheckMem(String checkMem) {
		this.checkMem = checkMem;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

}
