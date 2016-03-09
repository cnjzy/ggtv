package com.example.ddddd.vo;

import java.util.ArrayList;
import java.util.List;

public class ProductInfoVO extends BaseVO{
	private String name = "";
	private String imgUrl;
	private String target;
	private String action;
	private int authority;
	private String description;
	private String videoUrl;
	private int num;
	private String testUrl;
	private String testSecond;
	
	private String channel_name;
	private String more;
	private List<ProductInfoVO> content;
	private String message;
	private List<String> tags;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public String getMore() {
		return more;
	}
	public void setMore(String more) {
		this.more = more;
	}

	public List<ProductInfoVO> getContent() {
		if(content == null)
			content = new ArrayList<ProductInfoVO>();
		return content;
	}
	public void setContent(List<ProductInfoVO> content) {
		this.content = content;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getTags() {
		if(tags == null)
			tags = new ArrayList<String>();
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTestUrl() {
		return testUrl;
	}
	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}
	public String getTestSecond() {
		return testSecond;
	}
	public void setTestSecond(String testSecond) {
		this.testSecond = testSecond;
	}
	
}
