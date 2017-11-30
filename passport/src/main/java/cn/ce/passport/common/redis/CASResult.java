package cn.ce.passport.common.redis;

/**
 * cas执行结果
 * 
 * @author kyo.ou 2013-7-16
 * 
 * @param <T>
 */
public class CASResult<T> {
    public CASResult(boolean success, T finalResult) {
	this.success = success;
	this.finalResult = finalResult;
    }

    private boolean success;
    private T finalResult;

    /**
     * cas是否成功执行
     * 
     * @return
     */
    public boolean isSuccess() {
	return success;
    }

    /**
     * 最终结果
     * 
     * @return
     */
    public T getFinalResult() {
	return finalResult;
    }

}
