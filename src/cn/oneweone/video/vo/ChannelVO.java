package cn.oneweone.video.vo;

import java.util.List;

public class ChannelVO extends BaseVO{

	private List<ChannelVO> channel;
	private List<ProductInfoVO> video;
	
	private String channel_name;
	private String target;
	public List<ChannelVO> getChannel() {
		return channel;
	}
	public void setChannel(List<ChannelVO> channel) {
		this.channel = channel;
	}
	public List<ProductInfoVO> getVideo() {
		return video;
	}
	public void setVideo(List<ProductInfoVO> video) {
		this.video = video;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}
