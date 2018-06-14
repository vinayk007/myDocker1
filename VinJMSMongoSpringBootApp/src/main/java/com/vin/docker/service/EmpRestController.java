package com.vin.docker.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vin.docker.entity.Employee;

@RestController
public class EmpRestController {
	@Autowired
	private EmpService empServ;
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmp() {
		List<Employee> empList = empServ.getAllEmp();
		return new ResponseEntity<List<Employee>>(empList,HttpStatus.OK);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmpById(@PathVariable("id") String id) {
		Employee emp=null;
		try{
			emp = empServ.getEmployeeById(id);
		}catch(NoSuchElementException exp) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	}

	@GetMapping("/employees/name/{name}")
	public ResponseEntity<List<Employee>> getAllEmpByName(@PathVariable("name") String name) {
		List<Employee> empList = empServ.getEmployeeByName(name);
		if(empList.size() < 1) {
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Employee>>(empList,HttpStatus.OK);
	}

	@PostMapping("/employee")
	public ResponseEntity<Void> addOneEmp(@RequestBody Employee emp) {
		empServ.addEmployee(emp);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/employees/{id}")
	public void deleteEmpById(@PathVariable String id) {
		empServ.deleteEmployeeById(id);
	}

}
