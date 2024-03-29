package cn.ce.passport.dao.persistence;

import java.util.Date;

public class Role {

	private long roleId;
	private String roleName;
	private String description;
	private Date createTime;
	private int belongSys;
	private int status;
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public int getBelongSys() {
		return belongSys;
	}
	public void setBelongSys(int belongSys) {
		this.belongSys = belongSys;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
