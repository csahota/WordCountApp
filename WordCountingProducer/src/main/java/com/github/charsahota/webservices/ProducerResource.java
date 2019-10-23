package com.github.charsahota.webservices;

import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.github.charsahota.kafka.producer.KafkaProperties;
import com.github.charsahota.kafka.producer.Producer;

/**
 * Root resource (exposed at "rest" path)
 * 
 * Rest service for submitting word(s) to the producer.
 * 
 * @author Char Sahota
 * @date Oct. 22, 2019
 * 
 */
@Path("rest")
public class ProducerResource {

    /**
     * Splits the raw data to a word array and removes all characters
     * Traverses the array to submit each word individually to the Kafka cluster.
     * 
     * @param text
     * @return
     */
    @Path("/send/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String produce(String text) {
    	
    	System.out.println("Sent post : " + text);
    	String[] strArry = Arrays.stream(text.substring(9, text.length()-2).split("\\W+"))
    			  .map(String::trim)
    			  .toArray(String[]::new);
    	Producer producer = new Producer(KafkaProperties.TOPIC, true);
    	
    	for(String str : strArry) {
    		producer.sendMessage(str);
    	}
    	
    	producer.close();
    	
    	return "Success";
    }
}
