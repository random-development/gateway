package com.randomdevelopment.gateway;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.randomdevelopment.gateway.model.Metric;
import com.randomdevelopment.gateway.model.MonitorDB;
import com.randomdevelopment.gateway.model.MonitorInfo;

@RestController
@RequestMapping("monitor")
public class MonitorController {

	@Resource
	MonitorProvider monitorProvider;
	
	//@Autowired
	//private MonitorRepository monitorRepository;
	
	@PostMapping
	@RequestMapping(produces = "application/json", value = "/manage", method = RequestMethod.POST)
	public String manage(@RequestBody MonitorInfo info) {
		    
		/*final String uri = "http://hibron.usermd.net:5000/mock/monitors/monitor_hdmi/resources/komp_za_sciana/metrics/temp";
		RestTemplate restTemplate = new RestTemplate();
		Metric result = restTemplate.getForObject(uri, Metric.class);*/
		monitorProvider.setMonitor(info);
		
		//monitorRepository.save(new MonitorDB());
		
		return "OK";
	}
	
	@DeleteMapping
	@RequestMapping(produces = "application/json", value = "/manage", method = RequestMethod.DELETE)
	public String manageDel(@RequestBody MonitorInfo info) {
		    
		/*final String uri = "http://hibron.usermd.net:5000/mock/monitors/monitor_hdmi/resources/komp_za_sciana/metrics/temp";
		RestTemplate restTemplate = new RestTemplate();
		Metric result = restTemplate.getForObject(uri, Metric.class);*/
		monitorProvider.removeMonitor(info.getName());
		
		return "OK";
	}
	
}
