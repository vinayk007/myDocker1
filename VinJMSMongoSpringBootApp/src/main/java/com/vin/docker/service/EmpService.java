package com.vin.docker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.vin.docker.entity.EmpCrud;
import com.vin.docker.entity.Employee;
import com.vin.docker.entity.MessageTracker;
import com.vin.docker.entity.MyMongoRepository;

@Service
public class EmpService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmpService.class);
	@Autowired
	private EmpCrud empCrud;
	@Autowired
	public JmsTemplate jmsTemplate;
	@Autowired
	private MyMongoRepository myMongoRepo;
	
	public List<Employee> getAllEmp(){
		List<Employee> empList= new ArrayList<Employee>();
		for (Employee e : empCrud.findAll()) {
			empList.add(e);
		}		
		return empList;
	}
	
	public void addEmployee(Employee newEmp) {
        LOGGER.debug(" 1. Add Employee details in Employee table..");
		empCrud.save(newEmp);
		
		LOGGER.debug(" 2. Sending Employee to JMS queue for background check...");
        jmsTemplate.convertAndSend("com.jms.inboundQueue",newEmp);
    
        LOGGER.debug(" 3. Add Employee message in Tracker table..");
        myMongoRepo.save(new MessageTracker(newEmp.getEmpId(),"Submitted"));
	}

	public Employee getEmployeeById(String id) {
		Optional<Employee> emp = empCrud.findById(id);
		return emp.get();
	}
	
	public void deleteEmployeeById(String id) {
		empCrud.deleteById(id);
	}

	public List<Employee> getEmployeeByName(String name) {
		List<Employee> emp = empCrud.findByName(name);
		return emp;
	}

}
