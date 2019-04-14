package com.randomdevelopment.gateway.model;

public class ComplexMetric extends Metric {
	private String userId;
	private String sourceMetric;
	private Integer period;
	private Integer interval;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSourceMetric() {
		return sourceMetric;
	}
	public void setSourceMetric(String sourceMetric) {
		this.sourceMetric = sourceMetric;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
}
