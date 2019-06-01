package com.randomdevelopment.gateway;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.randomdevelopment.gateway.model.ComplexMetric;
import com.randomdevelopment.gateway.model.Metric;

public class MetricDeserializer extends StdDeserializer<Metric> { 
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MetricDeserializer() { 
        this(null); 
    } 
 
    public MetricDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public Metric deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        if (node.get("type") == null) {
        	return new Metric();
        }
        //System.out.println(node.asText());
        String type = (String) ( node.get("type")).asText();
        Metric m;
        //System.out.println(node.asText());
        if ( type.equals("complex")) {
        	m = new ComplexMetric();
        	((ComplexMetric)m).setUserId(node.get("userId").asText());
        	((ComplexMetric)m).setSourceMetric(node.get("sourceMetric").asText());
        	((ComplexMetric)m).setPeriod(node.get("period").intValue());
        	((ComplexMetric)m).setInterval(node.get("interval").intValue());
        } else {
        	m = new Metric();
        }
        m.setName(node.get("name").asText());
        m.setType(node.get("type").asText());
        return m;
    }
}