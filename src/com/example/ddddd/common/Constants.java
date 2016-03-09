package com.example.ddddd.common;

public class Constants {

    /************************************ Common ******************************/
    // 版本信息
    public static final String VERSION_NAME = "MMv";
    /**
     * 民瑞PC：http://10.8.9.140:8080
     * 测试：http://newapi1.etaoshi.com
     * 线上：http://newapi.etaoshi.com
     */
    public static final String BASE_URL = "http://api.76iw.com";
    public static final String PLATFORM = "Android";
    public static final String CHANNEL = "ets";

    /**
     * 支付金额
     */
    public static final int VIP_YEAR = 58;
//    public static final int VIP_TENURE = 1;
    public static final int VIP_TENURE = 38;
    public static final int VIP_YEAR_TO_TENURE = 40;
    
    /**
     * 支付类型
     */
    public static final int PAY_TYPE_ALIYUN = 1;
    public static final int PAY_TYPE_TECENT = 2;
    
    /**
     * 会员类型
     */
    public static final int MEMBER_TYPE_IS_NOT = 0;
    public static final int MEMBER_TYPE_IS_YEAR = 1;
    public static final int MEMBER_TYPE_IS_TENURE = 2;
    
    /**
     * 是否是debug模式
     */
    public static final boolean isDebug = false;

    /************************************ Net ******************************/
    public static boolean isGzip = true;
    // 链接次数
    public static final int CONNECTION_COUNT = 3;
    // 链接失败提示
    public static final String ERROR_MESSAGE = "小淘异常！请稍等！";
    // 链接失败提示
    public static final String ERROR_NO_NETWORK_MESSAGE = "网络异常，请检查网络连接是否正常";
    // 加载提示
    public static String LOADING_CONTENTS = "加载中，请稍候...";
    // 链接超时设置
    public static final int CONNECTION_SHORT_TIMEOUT = 10000;// 连接超时 5s
    public static final int READ_SHORT_TIMEOUT = 10000;// 连接超时 5s
    // 网络请求方式
    public static final String HTTP_POST = "POST";
    public static final String HTTP_GET = "GET";

    /******************************** Location *******************************/
    public static final int LOCATION_SCAN_SPAN = 30 * 1000;

    /******************************** API ************************************/
    /**
     *  首页列表
     */
    public static final String URL_HOME_LIST = BASE_URL + "";
    /**
     *  首页列表
     */
    public static final String URL_GET_USER_STATUS = BASE_URL + "/member/getUserType/";
    /**
     * 获取订单列表
     */
    public static final String URL_GET_ORDER = BASE_URL + "/order/createOrder/";
    /**
     * 获取频道分类列表
     */
    public static final String URL_GET_CHANNEL = BASE_URL + "/channel";
    /**
     *  vip视频列表
     */
    public static final String URL_GET_VIP_LIST = BASE_URL + "/vip";
    /**
     *  精品视频列表
     */
    public static final String URL_GET_FINE_LIST = BASE_URL + "/best";
    
    /**
     *  精品视频列表
     */
    public static final String URL_GET_VERSION = BASE_URL + "/index/getVersion/";
    
}
