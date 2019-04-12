package com.randomdevelopment.gateway.model;

public class Monitor {
	private String name;
	private Resource[] resources;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Resource[] getResources() {
		return resources;
	}
	public void setResources(Resource[] resources) {
		this.resources = resources;
	}
}
