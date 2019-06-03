package com.randomdevelopment.gateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping(value= {"gateway", "gateway-with-auth"})
public class ApiController {
	
	 @Resource
	 MonitorProvider monitorProvider;
	 

	 @NestedConfigurationProperty
	  private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

	  public AuthorizationCodeResourceDetails getClient() {
	    return client;
	  }
	  
	  @NestedConfigurationProperty
	  private ClientCredentialsResourceDetails client2 = new ClientCredentialsResourceDetails();

	  /*public ClientCredentialsResourceDetails getClient() {
	    return client;
	  }*/
	 
	 
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors10")
	public Metric monitors10() {
		    
		final String uri = "http://localhost:5000/mock/monitors/monitor_hdmi/resources/komp_za_sciana/metrics/temp";
		RestTemplate restTemplate = new RestTemplate();
		Metric result = restTemplate.getForObject(uri, Metric.class);
		
		return result;
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/removeresources")
	public String removeresources() {
		    
		for(Monitor monitor: monitorProvider.getMonitors()) {
			
			monitor.setResources(new MResource[] {});
		}
		
		return "ok";
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
		
		monitorProvider.rebuild();
		
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
	public Monitor[] monitors(OAuth2Authentication authentication) {
		if(authentication != null) {
			System.out.println("CLIENT ID = " + authentication.getName());//getClient().get());
		}
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
	@RequestMapping(produces = "application/json", value = "/monitors/{monitorName}/resources/{resourceName}/metrics",
			method=RequestMethod.GET)
	public Metric[] metrics(@PathVariable("monitorName") String monitorName, @PathVariable("resourceName") String resourceName) {
		final String monitorUri = monitorProvider.getMonitorUri(monitorName); 
		final String uri = monitorUri + MonitorApi.ResourcesPart + resourceName + "/" + MonitorApi.MetricsPart;
		if(monitorUri == null) return null;
		System.out.println(uri);
		RestTemplate restTemplate = new RestTemplate();
		Metric[] result = restTemplate.getForObject(uri, Metric[].class);
		
		return result;
	}
	
	@PostMapping
	@RequestMapping(produces = "application/json", value = "/monitors/{monitorName}/resources/{resourceName}/metrics",
			method=RequestMethod.POST)
	public ResponseEntity<?> metricsPost(@PathVariable("monitorName") String monitorName, @PathVariable("resourceName") String resourceName, @RequestBody String body) {
		final String monitorUri = monitorProvider.getMonitorUri(monitorName); 
		System.out.println("monitor uri:" + monitorUri);
		if(monitorUri == null) {
			 return new ResponseEntity<String>("{ \"error\":\"monitor not found\"}", HttpStatus.NOT_FOUND);
		}
		final String uri = monitorUri + MonitorApi.ResourcesPart + resourceName + "/" + MonitorApi.MetricsPart;
		System.out.println(uri);
		System.out.println(body);
		
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(uri);
	 
	    try {
		    String json = body;
		    StringEntity entity = new StringEntity(json);
		    httpPost.setEntity(entity);
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json");
		 
		    CloseableHttpResponse response = client.execute(httpPost);
		    int code = response.getStatusLine().getStatusCode();
		    HttpEntity entityResponse = response.getEntity();
		    String responseString = EntityUtils.toString(entityResponse, "UTF-8");
		    System.out.println(responseString);
		    client.close();
		    return new ResponseEntity<String>(responseString, HttpStatus.valueOf(code));
	    } catch (Exception e) {
	    	System.out.println("ERROR metricsPost");
	    	e.printStackTrace();
	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }		
	}
	
	@DeleteMapping
	@RequestMapping(produces = "application/json", value = "/monitors/{monitorName}/resources/{resourceName}/metrics/{metricName}",
			method=RequestMethod.DELETE)
	public ResponseEntity<?> metricsDelete(@PathVariable("monitorName") String monitorName, @PathVariable("resourceName") String resourceName, @PathVariable("metricName") String metricName) {
		final String monitorUri = monitorProvider.getMonitorUri(monitorName); 
		if(monitorUri == null) {
			 return new ResponseEntity<String>("{ \"error\":\"monitor not found\"}", HttpStatus.NOT_FOUND);
		}
		final String uri = monitorUri + MonitorApi.ResourcesPart + resourceName + "/" + MonitorApi.MetricsPart + metricName;
		if(monitorUri == null) return null;
		System.out.println(uri);
		
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpDelete httpDelete = new HttpDelete(uri);
	 
	    try {
		 
		    CloseableHttpResponse response = client.execute(httpDelete);
		    int code = response.getStatusLine().getStatusCode();
		    HttpEntity entityResponse = response.getEntity();
		    String responseString = EntityUtils.toString(entityResponse, "UTF-8");
		    System.out.println(responseString);
		    client.close();
		    return new ResponseEntity<String>(responseString, HttpStatus.valueOf(code));
	    } catch (Exception e) {
	    	System.out.println("ERROR metricsDelete");
	    	e.printStackTrace();
	    	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }		
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
