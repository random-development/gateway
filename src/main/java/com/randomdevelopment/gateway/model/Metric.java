package com.randomdevelopment.gateway.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.randomdevelopment.gateway.MetricDeserializer;

@JsonDeserialize(using = MetricDeserializer.class)
public class Metric {
	private String name;
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
