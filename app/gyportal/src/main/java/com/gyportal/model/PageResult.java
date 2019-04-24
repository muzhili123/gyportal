package com.gyportal.model;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 过期
 * 用于包装数据集的类
 */
public class PageResult {
	private List<?> content;

	private Integer totalElements;

	private Integer number;

	private Integer size;

	private double totalPages;

	private Integer numberOfElements;

	public List<?> getContent() {
		return content;
	}

	DecimalFormat df = new DecimalFormat("0.0");

	public void setContent(List<?> content) {
		this.content = content;
	}

	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		if (size == null && size <= 0) {
			this.size = 10;
		} else {
			this.size = size;
		}
	}

	public double getTotalPages() {
		return totalPages;
	}

	public void setTotalPages() {
		if (size == null && size <= 0) {
			this.size = 10;
		}
		this.totalPages = Math.ceil(Double.parseDouble(df.format((float) totalElements / size)));
	}

	public Integer getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(Integer numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

}
