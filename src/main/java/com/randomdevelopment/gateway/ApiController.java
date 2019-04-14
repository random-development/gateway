package com.randomdevelopment.gateway;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.randomdevelopment.gateway.model.Metric;
import com.randomdevelopment.gateway.model.Monitor;
import com.randomdevelopment.gateway.model.MResource;

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
		
		return result;
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors")
	public Monitor[] monitors() {
		return monitorProvider.getMonitorsData("monitors/").getMonitors();
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/{monitorName}")
	public MResource[] resources(@PathVariable("monitorName") String monitorName) {
		final String monitorUri = monitorProvider.getMonitorUri(monitorName); 
		final String uri = monitorUri;
		if(monitorUri == null) return null;
		System.out.println(uri);
		RestTemplate restTemplate = new RestTemplate();
		MResource[] result = restTemplate.getForObject(uri, MResource[].class);
		
		return result;
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/{monitorName}/resources/{resourceName}")
	public MResource resource(@PathVariable("monitorName") String monitorName, @PathVariable("resourceName") String resourceName) {
		final String monitorUri = monitorProvider.getMonitorUri(monitorName); 
		final String uri = monitorUri + MonitorApi.ResourcesPart + resourceName;
		if(monitorUri == null) return null;
		System.out.println(uri);
		RestTemplate restTemplate = new RestTemplate();
		MResource result = restTemplate.getForObject(uri, MResource.class);
		
		return result;
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/{monitorName}/resources/{resourceName}/metrics")
	public Metric[] metrics(@PathVariable("monitorName") String monitorName, @PathVariable("resourceName") String resourceName) {
		final String monitorUri = monitorProvider.getMonitorUri(monitorName); 
		final String uri = monitorUri + MonitorApi.ResourcesPart + resourceName + "/" + MonitorApi.MetricsPart;
		if(monitorUri == null) return null;
		System.out.println(uri);
		RestTemplate restTemplate = new RestTemplate();
		Metric[] result = restTemplate.getForObject(uri, Metric[].class);
		
		return result;
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/{monitorName}/resources/{resourceName}/metrics/{metricName}")
	public Metric metrics(@PathVariable("monitorName") String monitorName, @PathVariable("resourceName") String resourceName,
			@PathVariable("metricName") String metricName) {
		final String monitorUri = monitorProvider.getMonitorUri(monitorName); 
		final String uri = monitorUri + MonitorApi.ResourcesPart + resourceName + "/" + MonitorApi.MetricsPart + metricName;
		if(monitorUri == null) return null;
		System.out.println(uri);
		RestTemplate restTemplate = new RestTemplate();
		Metric result = restTemplate.getForObject(uri, Metric.class);
		
		return result;
	}
}
