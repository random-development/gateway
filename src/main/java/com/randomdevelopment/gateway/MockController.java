package com.randomdevelopment.gateway;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mock")
public class MockController {
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors")
	public String monitors() {
		return "[\r\n" + 
				"    {\r\n" + 
				"        \"name\": \"monitor_hdmi\",\r\n" + 
				"        \"resources\": \r\n" + 
				"        [\r\n" + 
				"            {\r\n" + 
				"                    \"name\": \"komp_pod_biurkiem\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                    \"name\": \"komp_za_sciana\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"temp\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"name\": \"monitor_komplex\",\r\n" + 
				"        \"resources\": \r\n" + 
				"        [\r\n" + 
				"            {\r\n" + 
				"                    \"name\": \"przegrzewajacy_sie_komp\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"temp\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        },  \r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"complex\",\r\n" + 
				"                            \"userId\": \"132\",\r\n" + 
				"                            \"sourceMetric\": \"cpu\",\r\n" + 
				"                            \"period\": 180,\r\n" + 
				"                            \"interval\": 60\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"name\": \"monitor_dsub\",\r\n" + 
				"        \"resources\": \r\n" + 
				"        [\r\n" + 
				"            {\r\n" + 
				"                    \"name\": \"komputer_w_stodole\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"name\": \"monitor_bez_kompa\",\r\n" + 
				"        \"resources\": \r\n" + 
				"        [\r\n" + 
				"        ]\r\n" + 
				"    }\r\n" + 
				"]";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_hdmi")
	public String monitorsHdmi() {
		return " [\r\n" + 
				"            {\r\n" + 
				"                    \"name\": \"komp_pod_biurkiem\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                    \"name\": \"komp_za_sciana\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"temp\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            }\r\n" + 
				"]";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_komplex")
	public String monitorsKomplex() {
		return "[\r\n" + 
				"            {\r\n" + 
				"                    \"name\": \"przegrzewajacy_sie_komp\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"temp\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        },  \r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpuk\",\r\n" + 
				"                            \"type\": \"complex\",\r\n" + 
				"                            \"userId\": \"132\",\r\n" + 
				"                            \"sourceMetric\": \"cpu\",\r\n" + 
				"                            \"period\": 180,\r\n" + 
				"                            \"interval\": 60\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            }\r\n" + 
				"        ]";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_dsub")
	public String monitorsDsub() {
		return "[\r\n" + 
				"            {\r\n" + 
				"                    \"name\": \"komputer_w_stodole\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            }\r\n" + 
				"]\r\n" + 
				"";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_bez_kompa")
	public String monitorsBezK() {
		return "[]";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_hdmi/resources/komp_pod_biurkiem")
	public String monitorsResKompP() {
		return "{\r\n" + 
				"                    \"name\": \"komp_pod_biurkiem\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            }";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_hdmi/resources/komp_za_sciana")
	public String monitorsResKompZa() {
		return "{\r\n" + 
				"                    \"name\": \"komp_za_sciana\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"temp\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            }";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_komplex/resources/przegrzewajacy_sie_komp")
	public String monitorsResPrzeg() {
		return "{\r\n" + 
				"                    \"name\": \"przegrzewajacy_sie_komp\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"temp\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        },  \r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpuk\",\r\n" + 
				"                            \"type\": \"complex\",\r\n" + 
				"                            \"userId\": \"132\",\r\n" + 
				"                            \"sourceMetric\": \"cpu\",\r\n" + 
				"                            \"period\": 180,\r\n" + 
				"                            \"interval\": 60\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            }";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_dsub/resources/komputer_w_stodole")
	public String monitorsResKompWS() {
		return " {\r\n" + 
				"                    \"name\": \"komputer_w_stodole\",\r\n" + 
				"                    \"metrics\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]\r\n" + 
				"            }";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_hdmi/resources/komp_pod_biurkiem/metrics")
	public String monitors1() {
		return "[\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_hdmi/resources/komp_za_sciana/metrics")
	public String monitors2() {
		return "[\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"temp\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_komplex/resources/przegrzewajacy_sie_komp/metrics")
	public String monitors3() {
		return "[\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"temp\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        },  \r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpuk\",\r\n" + 
				"                            \"type\": \"complex\",\r\n" + 
				"                            \"userId\": \"132\",\r\n" + 
				"                            \"sourceMetric\": \"cpu\",\r\n" + 
				"                            \"period\": 180,\r\n" + 
				"                            \"interval\": 60\r\n" + 
				"                        }\r\n" + 
				"                    ]";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_dsub/resources/komputer_w_stodole/metrics")
	public String monitors4() {
		return "[\r\n" + 
				"                        {\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }\r\n" + 
				"                    ]";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_hdmi/resources/komp_pod_biurkiem/metrics/cpu")
	public String monitors5() {
		return "{\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_hdmi/resources/komp_za_sciana/metrics/temp")
	public String monitors6() {
		return "{\r\n" + 
				"                            \"name\": \"temp\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_komplex/resources/przegrzewajacy_sie_komp/metrics/temp")
	public String monitors7() {
		return "{\r\n" + 
				"                            \"name\": \"temp\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_komplex/resources/przegrzewajacy_sie_komp/metrics/cpuk")
	public String monitors8() {
		return "{\r\n" + 
				"                            \"name\": \"cpuk\",\r\n" + 
				"                            \"type\": \"complex\",\r\n" + 
				"                            \"userId\": \"132\",\r\n" + 
				"                            \"sourceMetric\": \"cpu\",\r\n" + 
				"                            \"period\": 180,\r\n" + 
				"                            \"interval\": 60\r\n" + 
				"                        }";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/monitor_dsub/resources/komputer_w_stodole/metrics/cpu")
	public String monitors9() {
		return "{\r\n" + 
				"                            \"name\": \"cpu\",\r\n" + 
				"                            \"type\": \"normal\"\r\n" + 
				"                        }";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/{mname}/resources/{rname}/metrics/{mnamee}/measurements")
	public String monitors10() {
		return "{\r\n" + 
				"    \"columns\": [\"order\", \"timestamp\", \"value\"],\r\n" + 
				"    \"data\": [\r\n" + 
				"        [\"1\", \"1554663615\", \"213\"],\r\n" + 
				"        [\"2\", \"1554663616\", \"214\"],\r\n" + 
				"        [\"3\", \"1554663617\", \"215\"],\r\n" + 
				"        [\"4\", \"1554663618\", \"264\"],\r\n" + 
				"        [\"5\", \"1554663619\", \"214\"]\r\n" + 
				"    ]\r\n" + 
				"}";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/monitors/{mname}/resources/{rname}/metrics/{mnamee}/measurementss")
	public String monitors100() {
		return "[\r\n" + 
				"    {\r\n" + 
				"        \"time\": 1234567890,\r\n" + 
				"        \"value\": 11.1\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"time\": 1234567891,\r\n" + 
				"        \"value\": 12.1\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"time\": 1234567892,\r\n" + 
				"        \"value\": 15.1\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"time\": 1234567893,\r\n" + 
				"        \"value\": 17.1\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"time\": 1234567894,\r\n" + 
				"        \"value\": 91.1\r\n" + 
				"    }\r\n" + 
				"]";
	}
	
	@DeleteMapping
	@PostMapping
	@RequestMapping(value = "/monitors/{mname}/resources/{kname}/metrics")
	public String monitors11() {
		return "dziekujemy za dodanie/usuniecie metryki kompleksowej ";
	}
	
	@GetMapping
	@RequestMapping(produces = "application/json", value = "/metrics")
	public String metrics() {
		return "[\r\n" + 
				"    {\r\n" + 
				"        \"name\": \"hostName\",\r\n" + 
				"        \"type\": \"cpu\",\r\n" + 
				"        \"lastValue\": 50,\r\n" + 
				"        \"time\": 1554663619,\r\n" + 
				"        \"timeData\": [1554663615, 1554663616, 1554663617, 1554663618, 1554663619],\r\n" + 
				"        \"valueData\": [213, 214, 215, 264, 214]\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"name\": \"hostName2\",\r\n" + 
				"        \"type\": \"temp\",\r\n" + 
				"        \"lastValue\": 99,\r\n" + 
				"        \"time\": 1554643619,\r\n" + 
				"        \"timeData\": [1554643615, 1554643616, 1554643617, 1554643618, 1554643619],\r\n" + 
				"        \"valueData\": [78, 88, 97, 98, 99]\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"name\": \"trzeci\",\r\n" + 
				"        \"type\": \"temp\",\r\n" + 
				"        \"lastValue\": 125,\r\n" + 
				"        \"time\": 1554643630,\r\n" + 
				"        \"timeData\": [1554643615, 1554643616, 1554643617, 1554643618, 1554643619, 1554643620, 1554643625, 1554643630],\r\n" + 
				"        \"valueData\": [78, 88, 97, 98, 99, 100, 120, 125]\r\n" + 
				"    }\r\n" + 
				"]";
	}
}
