package cn.ce.passport.common.exception;

public enum ExceptionCode {
	
	/* user */
	USER_EMPTY("用户信息为空"),
	USER_ERROR("用户信息错误")
	;

	private String description;

	ExceptionCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
