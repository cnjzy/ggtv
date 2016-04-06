package cn.oneweone.video.procotol;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.reflect.TypeToken;
import cn.oneweone.video.MyApp;
import cn.oneweone.video.util.LogUtil;
import cn.oneweone.video.util.StringUtil;

/**
 * 消息响应基类
 * 
 * @author jzy
 * 
 */
public abstract class ResponseMessage implements MessageIF {
	private JSONParser parser;
	private boolean success;
	private String message;
	private int pageCount;
	private int pageNo;
	
	public ResponseMessage() {
		parser = new JSONParser();
	}
	
	private void parseHeader(JSONObject obj) throws Exception {
		if (obj.containsKey("status")) {
			success = Integer.parseInt(obj.get("status").toString()) == 200;
		} 
		if (obj.containsKey("msg")) {
			message = (String) obj.get("msg");
		} else {
			message = "数据解析异常，请重试";
		}
		if(obj.containsKey("pageCount")){
			pageCount = Integer.parseInt(obj.get("pageCount").toString());
		}
		if(obj.containsKey("pageNo")){
			pageNo = Integer.parseInt(obj.get("pageNo").toString());
		}
	}

	public void parseResponse(String jsonStr,TypeToken<?> type,TypeToken<?> typeList,TypeToken<?> typeMap) throws ParseException {
		try {
			parseResponse(jsonStr, type, typeList, true);
		} catch (ParseException e) {
			throw e;
		}
	}
	public void parseResponse(String jsonStr,TypeToken<?> type,TypeToken<?> typeList) throws ParseException {
		try {
			parseResponse(jsonStr, type, typeList, true);
		} catch (ParseException e) {
			throw e;
		}
	}
	public void parseResponse(String jsonStr,TypeToken<?> type) throws ParseException {
		try {
			parseResponse(jsonStr, type, type, true);
		} catch (ParseException e) {
			throw e;
		}
	}

	public void parseResponse(String jsonStr,TypeToken<?> type,TypeToken<?> typeList, boolean isShowError)
			throws ParseException {
		if (jsonStr == null || jsonStr.equals("")) {
			LogUtil.i("test", "result is null");
			return;
		}
		try {
			JSONObject obj = (JSONObject) parser.parse(jsonStr);
			parseHeader(obj);
			if (success) {// 请求成功，有结果返回
				// todo 状态判断
				parseBody(obj,type,typeList);
			} else {
				if(isShowError && StringUtil.isNotEmpty(message))
					MyApp.showToast(message);
			}
		} catch (ParseException pe) {
			message = "数据解析异常，请重试";
			throw pe;
		} catch (Exception ex) {
			message = "数据解析异常，请重试";
			ex.printStackTrace();
		}
	}

	/**
	 * 解析消息体
	 * 
	 * @param obj
	 */
	protected abstract void parseBody(JSONObject obj,TypeToken<?> type,TypeToken<?> typeList) throws ParseException;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
