package com.vin.docker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.vin.docker.entity.Employee;
import com.vin.docker.entity.MessageTracker;
import com.vin.docker.entity.MyMongoRepository;

@Component
public class MyJmsReceiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyJmsReceiver.class);

	@Autowired
	private MyMongoRepository myMongoRepo;

	@JmsListener(destination="com.jms.inboundQueue")
	 public void receiveMessage(Employee msg) {
		LOGGER.debug(" 4. JMS Received " + msg );
		LOGGER.debug(" 5. Background check done for - " + msg.getName() );
        
		LOGGER.debug(" 6. Update Employee message in Tracker table..");
        myMongoRepo.save(new MessageTracker(msg.getEmpId(),"Completed"));

   }
}		
