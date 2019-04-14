package com.randomdevelopment.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.randomdevelopment.gateway.model.Metric;
import com.randomdevelopment.gateway.model.Monitor;
import com.randomdevelopment.gateway.model.Monitors;
import com.randomdevelopment.gateway.model.MResource;
import com.randomdevelopment.gateway.model.Resources;

public class MonitorProvider {
	private HashMap<String, String> monitorUris;
	
	public MonitorProvider()
	{
		monitorUris = new HashMap<>();
		monitorUris.put("monitor_hdmi", "http://hibron.usermd.net:5000/mock/monitors/monitor_hdmi/");
		monitorUris.put("monitor_komplex", "http://hibron.usermd.net:5000/mock/monitors/monitor_komplex/");
		monitorUris.put("monitor_dsub", "http://hibron.usermd.net:5000/mock/monitors/monitor_dsub/");
		monitorUris.put("monitor_bez_kompa", "http://hibron.usermd.net:5000/mock/monitors/monitor_bez_kompa/");
	}

	public HashMap<String, String> getMonitorUris() {
		return monitorUris;
	}

	public void setMonitorUris(HashMap<String, String> monitorUris) {
		this.monitorUris = monitorUris;
	}
	
	public String getMonitorUri(String monitorName) {
		return monitorUris.get(monitorName);
	}
	
	public Monitors getMonitorsData(String dataUri) {
		
		Monitors monitors = new Monitors();
		ArrayList<Monitor> monitorsList = new ArrayList<>();
		for(Map.Entry<String, String> entry : monitorUris.entrySet()) {
		    String key = entry.getKey();
		    String monitorUri = entry.getValue();

		    final String uri = monitorUri;
			RestTemplate restTemplate = new RestTemplate();
			System.out.println(uri);
			MResource[] resources = restTemplate.getForObject(uri, MResource[].class);
			
			Monitor monitor = new Monitor();
			monitor.setName(key);
			monitor.setResources(resources);
			monitorsList.add(monitor);
		}
		Monitor[] ms = monitorsList.toArray(new Monitor[monitorsList.size()]);
		monitors.setMonitors(ms);
		return monitors;
	}
}
