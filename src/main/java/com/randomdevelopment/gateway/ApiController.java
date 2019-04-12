package com.randomdevelopment.gateway;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.randomdevelopment.gateway.model.Metric;
import com.randomdevelopment.gateway.model.Monitor;
import com.randomdevelopment.gateway.model.Monitors;

@RestController
@RequestMapping("gateway")
public class ApiController {
	
	 @Resource
	 MonitorProvider monitorProvider;
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors10")
	public Metric monitors10() {
		    
		final String uri = "http://hibron.usermd.net:5000/mock/monitors/monitor_hdmi/resources/komp_za_sciana/metrics/temp";
		RestTemplate restTemplate = new RestTemplate();
		Metric result = restTemplate.getForObject(uri, Metric.class);
		
		/*Metric m = new Metric();
		m.setName("namee");
		m.setType("tup[");*/
		return result;
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors")
	public Monitor[] monitors() {
		return monitorProvider.getMonitorsData("monitors/").getMonitors();
	}
}
