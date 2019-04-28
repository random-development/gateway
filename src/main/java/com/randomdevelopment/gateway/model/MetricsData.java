package com.randomdevelopment.gateway.model;

public class MetricsData {
	private String name;
	private String type;
	private Double lastValue;
	private Long time;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getLastValue() {
		return lastValue;
	}
	public void setLastValue(Double lastValue) {
		this.lastValue = lastValue;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public long[] getTimeData() {
		return timeData;
	}
	public void setTimeData(long[] timeData) {
		this.timeData = timeData;
	}
	public double[] getValueData() {
		return valueData;
	}
	public void setValueData(double[] valueData) {
		this.valueData = valueData;
	}
	private long[] timeData;
	private double[] valueData;
}
