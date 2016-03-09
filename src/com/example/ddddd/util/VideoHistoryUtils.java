package com.example.ddddd.util;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.BaseJson;

import u.aly.da;
import android.text.TextUtils;

import com.example.ddddd.MyApp;
import com.example.ddddd.vo.ProductInfoVO;
import com.google.gson.reflect.TypeToken;

public class VideoHistoryUtils {

	private final static String KEY = "video_history";
	
	/**
	 * 添加历史记录
	 * @param vo
	 */
	public static void addHistory(ProductInfoVO vo){
		if(vo != null && !TextUtils.isEmpty(vo.getName())){
			String json = MyApp.preferencesUtils.getString(KEY, "");
			List<ProductInfoVO> dataList = (List<ProductInfoVO>) BaseJson.parser(new TypeToken<List<ProductInfoVO>>(){}, json);
			if(dataList == null){
				dataList = new ArrayList<ProductInfoVO>();
				dataList.add(vo);
			}else{
				for (int i = 0; i < dataList.size(); i++) {
					if(vo.getName().equals(dataList.get(i).getName())){
						break;
					}else if(i == dataList.size() -1){
						dataList.add(vo);
					}
				}
			}
			MyApp.preferencesUtils.putString(KEY, BaseJson.toJson(dataList));
		}
	}
	
	/**
	 * 获取历史记录信息
	 * @return
	 */
	public static List<ProductInfoVO> getHistory(){
		String json = MyApp.preferencesUtils.getString(KEY, "");
		List<ProductInfoVO> dataList = (List<ProductInfoVO>) BaseJson.parser(new TypeToken<List<ProductInfoVO>>(){}, json);
		if(dataList == null){
			dataList = new ArrayList<ProductInfoVO>();
		}
		return dataList;
	}
	
	/**
	 * 删除历史记录
	 * @param vo
	 */
	public static void removeHistory(ProductInfoVO vo){
		if(vo != null && !TextUtils.isEmpty(vo.getName())){
			String json = MyApp.preferencesUtils.getString(KEY, "");
			List<ProductInfoVO> dataList = (List<ProductInfoVO>) BaseJson.parser(new TypeToken<List<ProductInfoVO>>(){}, json);
			for (int i = 0; dataList != null && i < dataList.size(); i++) {
				if(vo.getName().equals(dataList.get(i).getName())){
					dataList.remove(i);
					break;
				}
			}
			MyApp.preferencesUtils.putString(KEY, BaseJson.toJson(dataList));
		}
	}
}
