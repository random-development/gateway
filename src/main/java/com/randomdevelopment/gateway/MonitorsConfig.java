package com.randomdevelopment.gateway;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;

import com.randomdevelopment.gateway.model.Monitor;

@Configuration
public class MonitorsConfig {
	private ArrayList<Monitor> monitors;

	public ArrayList<Monitor> getMonitors() {
		return monitors;
	}

	public void setMonitors(ArrayList<Monitor> monitors) {
		this.monitors = monitors;
	}
}
