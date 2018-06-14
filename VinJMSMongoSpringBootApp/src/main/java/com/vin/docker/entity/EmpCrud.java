package com.vin.docker.entity;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmpCrud extends MongoRepository<Employee, String> {

	public List<Employee> findByName(String name);
	
}
