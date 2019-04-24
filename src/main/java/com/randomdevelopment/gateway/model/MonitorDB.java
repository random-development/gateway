package com.randomdevelopment.gateway.model;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="monitor")
public class MonitorDB implements Serializable {
	
	public MonitorDB() {
		data = new HashMap<>();
	}
	
	public MonitorDB(Long id, HashMap<String, String> data) {
		this.id = id;
		this.data = data;
	}

	@Id
	private Long id = (long) 1;
	private HashMap<String, String> data;

	public HashMap<String, String> getData() {
		return data;
	}

	public void setData(HashMap<String, String> data) {
		this.data = data;
	}
}
