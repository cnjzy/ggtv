package cn.oneweone.video.net.utils;

public class ErrorUtil {
	public static String errorJson(String resultCode,String message){
		return "{\"success\":\""+resultCode+"\",\"message\":\""+message+"\"}";
	}
}
