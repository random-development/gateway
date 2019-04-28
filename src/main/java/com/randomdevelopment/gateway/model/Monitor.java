package com.randomdevelopment.gateway.model;

public class Monitor {
	private String name;
	private MResource[] resources;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MResource[] getResources() {
		return resources;
	}
	public void setResources(MResource[] resources) {
		for(MResource resource: resources) {
			resource.setMonitorName(name);
		}
		this.resources = resources;
	}
}
