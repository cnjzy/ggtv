package com.example.ddddd.vo;

import java.util.List;

public class ProductDetailVO extends BaseVO{

	private ProductInfoVO details;
	private List<ProductInfoVO> hot;
	public ProductInfoVO getDetails() {
		return details;
	}
	public void setDetails(ProductInfoVO details) {
		this.details = details;
	}
	public List<ProductInfoVO> getHot() {
		return hot;
	}
	public void setHot(List<ProductInfoVO> hot) {
		this.hot = hot;
	}
}
