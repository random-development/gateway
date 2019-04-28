package com.randomdevelopment.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MResource {
	private String name;
	private Metric[] metrics;
	@JsonIgnore
	private String monitorName;
	public String getMonitorName() {
		return monitorName;
	}
	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}
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
