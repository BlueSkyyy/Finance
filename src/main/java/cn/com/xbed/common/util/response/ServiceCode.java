package cn.com.xbed.common.util.response;


/**
 * 业务返回参数
* @Title: ServiceCode.java 
* @Package cn.com.xbed.util.response 
* @Description: TODO
* @author porridge  
* @date 2015年11月26日 下午1:07:40 
* @version V1.0
 */
public class ServiceCode {
	public static final int OK = 21020000;
    public static final String OK_DEFAULT_MSG = "操作成功";
    
    public static final int DELETE_SUCCESS = 22020001;
    public static final String DELETE_SUCCESS_MSG = "删除成功";
    
    public static final int DELETE_FAILURE = 42020002;
    public static final String DELETE_FAILURE_MSG = "删除失败";
    
    public static final int UNAUTHORIZED = 42020003;
    public static final String UNAUTHORIZED_DEFAULT_MSG = "未授权,或授权过期";

    public static final int MISSING_PARAM_CODE = 42020004;
    public static final String MISSING_PARAM_CODE_DEFAULT_MSG = "缺少参数";
    
    public static final int PAGE_NOT_FOUND = 42020005;
    public static final String PAGE_NOT_FOUND_DEFAULT_MSG = "找不到请求的页面";

    public static final int RESOURCE_NOT_FOUND = 42020006;
    public static final String RESOURCE_NOT_FOUND_DEFAULT_MSG = "找不到请求的资源";
    
    //无效参数
    public static final int INVILD_PARAM_CODE = 42020007;
    public static final String INVILD_PARAM_DEFAULT_MSG = "无效参数";
    
    public static final int OTHER_API_ERROR_CODE = 42020008;
    public static final String OTHER_API_ERROR_CODE_DEFAULT_MSG = "%s服务发生错误,传递数据：%s 服务返回结果：%s";
    
    public static final int ERROR = 42020000;
    public static final String ERROR_DEFAULT_MSG = "操作失败";
    
    public static final int SERVER_ERROR = 52020000;
    public static final String SERVER_ERROR_DEFAULT_MSG = "系统异常，请重试";

    public static final int SERVER_REPETITION = 62020001;
    public static final String SERVER_REPETITION_DEFAULT_MSG = "操作失败，存在重复数据";
    
    public static final int EXIST_PHONE_ERROR = 62020002;
    public static final String EXIST_PHONE_ERROR_DEFAULT_MSG = "用户手机号码已存在，不能够重复注册";
    
    public static final  int VERIFY_PHONE_CODE_ERROR = 62020003;
    public static final String VERIFY_PHONE_CODE_ERROR_DEFAULT_MSG = "短信验证码输入错误或过期";

    public static final int CHECKIN_CLEAN_CODE = 72020001;
    public static final String CHECKIN_CLEAN_CODE_DEFAULT_MSG = "需要在住清洁评价";

    public static final int CHECKIN_CLEAN_TIME_CODE = 72020002;
    public static final String CHECKIN_CLEAN_TIME_CODE_DEFAULT_MSG = "可申请在住清洁";

    public static final int UNDER_APPLY_CANCEL_CODE = 72020003;
    public static final String UNDER_APPLY_CANCEL_CODE_DEFAULT_MSG = "已经申请了在住清洁，再次申请需取消上一单，确定取消吗";

    public static final int UNDER_APPLY_CODE = 72020004;
    public static final String CUNDER_APPLY_CODE_DEFAULT_MSG = "清洁管家已经出发前往打扫，不能再次申请";

    public static final int CHECKINID_VALID_CODE = 72020005;
    public static final String CHECKINID_VALID_CODE_DEFAULT_MSG = "该入住单不存在";

    public static final int CHECKINTIME_VALID_CODE = 72020006;
    public static final String CHECKINTIME_VALID_CODE_DEFAULT_MSG = "申请在住清洁时间超出范围";

    public static final int STEP_VALID_CODE = 72020007;
    public static final String STEP_VALID_CODE_DEFAULT_MSG = "该入住单不在入住状态";

    public static final int CLEAN_TIME_VALID_CODE = 72020008;
    public static final String CLEAN_TIME_VALID_CODE_DEFAULT_MSG = "对不起，目前不能申请在住清洁";

    public static final int CHECKIN_CLEAN_ERROR = 72029999;
    public static final String CHECKIN_CLEAN_ERROR_DEFAULT_MSG = "调用丽佳会检查是否允许申请在住清洁接口发生错误";

    public static final String CANCEL_CHECKINORDER_OVER_TIME_MSG = "对不起，已经超过14点，订单不能取消";

    public static final String CHECKIN_STATUS_MSG = "已经有人办理了入住，订单不能取消";

    public static final int EVALUEATED_CODE = 72020009;
    public static final String EVALUEATED_CODE_DEFAULT_MSG = "您已评价过该房间";

    public static final int PAY_NEEDED_CLEAN_ORDER = 72020010;
    public static final String PAY_NEEDED_CLEAN_ORDER_DEFAULT_MSG = "该请结单需要用户支付";

}