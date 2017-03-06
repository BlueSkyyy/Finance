package cn.com.xbed.common.util.response;


/**
 * 统一返回格式
* @Title: ResultBean.java 
* @Package cn.com.xbed.constant 
* @Description: TODO
* @author porridge  
* @date 2015年11月26日 下午12:54:42 
* @version V1.0
 */
public class ResultBean<T> {
	private Boolean status;
	private Integer retCode;
	private String msg;
	private T data;
	
	public ResultBean() {
		super();
	}
	
	public ResultBean(Boolean status, Integer retCode, String msg, T data) {
		super();
		this.status = status;
		this.retCode = retCode;
		this.msg = msg;
		this.data = data;
	}

	public ResultBean(Boolean status, Integer retCode, String msg) {
		super();
		this.retCode = retCode;
		this.msg = msg;
	}
	public ResultBean(Integer retCode) {
		super();
		this.retCode = retCode;
	}
	public ResultBean(Integer retCode, String msg, T data) {
		super();
		this.retCode = retCode;
		this.msg = msg;
		this.data = data;
	}
	public Integer getRetCode() {
		return retCode;
	}
	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResultBean [status=" + status + ", retCode=" + retCode
				+ ", msg=" + msg + ", data=" + data + "]";
	}
	
}
