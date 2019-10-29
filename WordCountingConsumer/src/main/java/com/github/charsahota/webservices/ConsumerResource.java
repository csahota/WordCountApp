package com.github.charsahota.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.github.charsahota.kafka.consumer.Consumer;
import com.github.charsahota.kafka.consumer.KafkaProperties;
import com.github.charsahota.kafka.model.WordResponse;

/**
 * Root resource (exposed at "rest" path)
 * 
 * Rest service for retrieving word data from a consumer.
 * 
 * @author Char Sahota
 * @date Oct. 22, 2019
 * 
 * Edit: Oct. 26, 2019
 * Method: getWordData
 * 1. Introduced the WordResponse object for creating a JSON string return.
 *
 */
@Path("rest")
public class ConsumerResource {

	/**
	 * A JSONObject is passed in with the text element sent to the Consumer 
	 * for retrieving word data.
	 * 
	 * @param word
	 * @return
	 */
	@Path("/word/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWordData(String word) {
		
		System.out.println("Consmer Post : " + word);

		JSONObject jsonObj = new JSONObject(word);
		Consumer consumer = new Consumer(KafkaProperties.TOPIC);
		WordResponse data = consumer.retreiveWordRecords(jsonObj.getString("text"));

		System.out.println("Consmer Data : " + data);
		
	    return Response.status(Response.Status.OK).entity(data).build();

	}

}
