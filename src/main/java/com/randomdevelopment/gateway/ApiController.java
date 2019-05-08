package com.randomdevelopment.gateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.randomdevelopment.gateway.model.Metric;
import com.randomdevelopment.gateway.model.MetricsData;
import com.randomdevelopment.gateway.model.Monitor;
import com.randomdevelopment.gateway.model.MResource;
import com.randomdevelopment.gateway.model.Measurement;

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
	@RequestMapping(produces = "application/json", value = "/metrics")
	public List<MetricsData> metricsFilter(@RequestParam(value = "resources", required = false) String resourcesData, 
			@RequestParam(value = "resourceName", required = false) String resourceName,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to,
			@RequestParam(value = "limit", required = false) String limit) {
		System.out.println(resourcesData);
		
		List<MResource> resourcesFiltered = new ArrayList<>(); 
		
		//resources filter
		if(resourcesData != null) {
			String[] monitors = resourcesData.split(",");
			HashMap<String, String[]> resourcesMap = new HashMap<>();
			for(String monitor: monitors) {
				String[] monitorsAndResources = monitor.split(":");
				if(monitorsAndResources.length > 1) {
					resourcesMap.put(monitorsAndResources[0], 
							Arrays.copyOfRange(monitorsAndResources, 1, monitorsAndResources.length));
				}else {
					resourcesMap.put(monitor, null);
				}
			}
			for(Monitor monitor: monitorProvider.getMonitors()) {
				if(resourcesMap.containsKey(monitor.getName())) {
					String[] resources = resourcesMap.get(monitor.getName());
					if(resources == null) {
						resourcesFiltered.addAll(Arrays.asList(monitor.getResources()));
					}else {
						for(String resource: resources) {
							for(MResource monitorResource: monitor.getResources()) {
								if(monitorResource.getName().equals(resource)) {
									resourcesFiltered.add(monitorResource);
								}
							}
						}
					}
				}
			}
		} else {
			System.out.println(monitorProvider.getMonitors().size());
			for(Monitor monitor: monitorProvider.getMonitors()) {
				
				resourcesFiltered.addAll(Arrays.asList(monitor.getResources()));
			}
		}
		
		System.out.println(resourcesFiltered.size());
		
		//resourceName filter
		if(resourceName != null) {
			for(int i=resourcesFiltered.size()-1; i>=0; i--) {
				if(! resourcesFiltered.get(i).getName().contains(resourceName)) {
					resourcesFiltered.remove(i);
				}
			}
		}
		
		//creating MetricsData
		List<MetricsData> metricsDatas = new ArrayList<>();
		for(MResource resource: resourcesFiltered) {
			for(Metric metric: resource.getMetrics()) {
				//type filter
				boolean inFilter = false;
				if(type != null) {
					String[] types = type.split(",");
					for(String singleType: types) {
						if (metric.getName().contains(singleType)) {
							inFilter = true;
							break;
						}
					}
				} else {
					inFilter = true;
				}
				if(inFilter) {
					/*MetricsData data = new MetricsData();
					data.setName(resource.getMonitorName());
					data.setType(metric.getType());*/

					MetricsData data = getMetricsData(resource.getMonitorName(),
							resource.getName(), metric.getName(), from, to, limit);
					if(data != null) {
						metricsDatas.add(getMetricsData(resource.getMonitorName(),
							resource.getName(), metric.getName(), from, to, limit));
					}
				}
			}
		}
		
		
		
		
		
		//return monitorProvider.getMonitorsData("monitors/").getMonitors();
		return metricsDatas;
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
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/{monitorName}/resources/{resourceName}/metrics/{metricName}/measurements")
	public MetricsData measurements(@PathVariable("monitorName") String monitorName, @PathVariable("resourceName") String resourceName,
			@PathVariable("metricName") String metricName,
			@Param("from") String paramFrom, @Param("to") String paramTo, @Param("limit") String paramLimit) {
		
		return getMetricsData(monitorName, resourceName, metricName, paramFrom, paramTo, paramLimit);
	}
	
	public MetricsData getMetricsData(String monitorName, String resourceName,
			String metricName,
			String paramFrom, String paramTo, String paramLimit) {
		final String monitorUri = monitorProvider.getMonitorUri(monitorName); 
		final String uri = monitorUri + MonitorApi.ResourcesPart + resourceName + "/" + MonitorApi.MetricsPart + metricName +
				"/" + MonitorApi.MeasurementsPart;
		if(monitorUri == null) return null;
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		if(paramFrom != null) {
			builder.queryParam("from", paramFrom);
		}
		if(paramTo != null) {
			builder.queryParam("to", paramTo);
		}
		if(paramLimit != null) {
			builder.queryParam("limit", paramLimit);
		}
		
		System.out.println(builder.toUriString());
		RestTemplate restTemplate = new RestTemplate();
		Measurement[] measurements = null;
		try {
			measurements = restTemplate.getForObject(builder.toUriString(), Measurement[].class);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		MetricsData data = new MetricsData();
		data.setResourceName(resourceName);
		data.setName(monitorName);
		data.setType(metricName);
		if(measurements.length>0) {
			data.setLastValue(measurements[measurements.length-1].getValue());
			data.setTime(measurements[measurements.length-1].getTime());
		}
		data.setValueData(Measurement.getValues(measurements));
		data.setTimeData(Measurement.getTimes(measurements));
		
		return data;
	}
}
