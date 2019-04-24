package com.randomdevelopment.gateway;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.randomdevelopment.gateway.model.Monitor;
import com.randomdevelopment.gateway.model.MonitorDB;

public interface MonitorRepository extends CrudRepository<MonitorDB, Long> {

}
