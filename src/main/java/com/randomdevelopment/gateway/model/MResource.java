package com.randomdevelopment.gateway.model;

public class MResource {
	private String name;
	private Metric[] metrics;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Metric[] getMetrics() {
		return metrics;
	}
	public void setMetrics(Metric[] metrics) {
		this.metrics = metrics;
	}
}
