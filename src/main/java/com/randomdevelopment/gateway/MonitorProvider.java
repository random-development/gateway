package com.randomdevelopment.gateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.randomdevelopment.gateway.model.Metric;
import com.randomdevelopment.gateway.model.Monitor;
import com.randomdevelopment.gateway.model.MonitorDB;
import com.randomdevelopment.gateway.model.MonitorInfo;
import com.randomdevelopment.gateway.model.Monitors;
import com.randomdevelopment.gateway.model.MResource;
import com.randomdevelopment.gateway.model.Resources;

@Service
@ComponentScan
@Controller
public class MonitorProvider {
	
	@Autowired
	private MonitorRepository monitorRepository;
	
	private HashMap<String, String> monitorUris;
	private List<Monitor> monitors;
	
	public List<Monitor> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<Monitor> monitors) {
		this.monitors = monitors;
	}

	public MonitorProvider()
	{
		monitorUris = new HashMap<>();
		monitors = new ArrayList<>();
		/*
		monitorUris.put("monitor_hdmi", "http://hibron.usermd.net:5000/mock/monitors/monitor_hdmi/");
		monitorUris.put("monitor_komplex", "http://hibron.usermd.net:5000/mock/monitors/monitor_komplex/");
		monitorUris.put("monitor_dsub", "http://hibron.usermd.net:5000/mock/monitors/monitor_dsub/");
		monitorUris.put("monitor_bez_kompa", "http://hibron.usermd.net:5000/mock/monitors/monitor_bez_kompa/");
		*/
	}
	
	@PostConstruct   
    public void init() {
		if(monitorRepository.count() > 0) {
			monitorUris = monitorRepository.findAll().iterator().next().getData();
			Collections.addAll(monitors, (getMonitorsData("TODO").getMonitors()));
			
		}
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
	
	public void setMonitor(MonitorInfo info) {
		Monitor monitor = getMonitorData(info);
		if (monitor == null) {
			return;
		}
		monitorUris.put(info.getName(), info.getUrl());
		boolean added = false;
		for (int i = 0; i < monitors.size(); i++) {
			if(monitors.get(i).getName().equals(info.getName())) {
				monitors.set(i, monitor);
				added = true;
				break;
			}
		}
		if( ! added) {
			monitors.add(monitor);
		}
		
		monitorRepository.deleteAll();
		monitorRepository.save(new MonitorDB((long)1, monitorUris));
	}
	
	public void removeMonitor(String monitorName) {
		monitorUris.remove(monitorName);
		for (int i = 0; i < monitors.size(); i++) {
			if(monitors.get(i).getName().equals(monitorName)) {
				monitors.remove(i);
				break;
			}
		}
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
			MResource[] resources = null;
			try {
				resources = restTemplate.getForObject(uri, MResource[].class);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			
			Monitor monitor = new Monitor();
			monitor.setName(key);
			monitor.setResources(resources);
			monitorsList.add(monitor);
		}
		Monitor[] ms = monitorsList.toArray(new Monitor[monitorsList.size()]);
		monitors.setMonitors(ms);
		return monitors;
	}
	
	public Monitor getMonitorData(MonitorInfo info) {
		final String uri = info.getUrl();
		RestTemplate restTemplate = new RestTemplate();
		System.out.println(uri);
		MResource[] resources = null;
		try {
			resources = restTemplate.getForObject(uri, MResource[].class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		Monitor monitor = new Monitor();
		monitor.setName(info.getName());
		monitor.setResources(resources);
		
		return monitor;
	}
}
