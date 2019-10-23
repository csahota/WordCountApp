package com.github.charsahota.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.charsahota.model.WordRequest;

/**
 * Client class
 * 
 * @author Char Sahota
 * @date Oct. 22, 2019
 *
 */
public class WordCountClient {

	private static final Logger logger = LogManager.getLogger(WordCountClient.class.getName());

	private final static String EMPTY_STRING_MSG = "Received empty or null string.";
	private final static String PRODUCER_INTERUPTED_MSG = "Producer was interupted, please try again.";
	private final static String CONSUMER_INTERUPTED_MSG = "Consumer was interupted, please try again.";

	/**
	 * Send entered text to Kafka Producer Service
	 * 
	 * @param messageStr
	 * @return
	 */
	public static String produceMessage(String messageStr) {

		if (messageStr == null || messageStr.isEmpty()) {

			return EMPTY_STRING_MSG;

		} else {

			try {
				Client client = ClientBuilder.newClient();

				WordRequest request = new WordRequest();
				request.setText(messageStr);
				Entity<WordRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);

				String targetResource = "http://localhost:8080/WordCountingProducer/webapi/rest/send/";
				Response response = client.target(targetResource).request(MediaType.APPLICATION_JSON).post(entity);

				String data = response.readEntity(String.class);

				logger.info("Response code " + response.getStatus());
				logger.info("Response from the server: " + data);

				return data;

			} catch (Exception e) {
				logger.error(PRODUCER_INTERUPTED_MSG, e);

				return PRODUCER_INTERUPTED_MSG;
			}
		}
	}

	/**
	 * Retreive the number of times a word was submitted to the Producer 
	 * along with the first and last times the word was entered.
	 * 
	 * @param word
	 * @return
	 */
	public static String consumeJsonWord(String word) {

		if (word == null || word.isEmpty()) {

			return EMPTY_STRING_MSG;

		} else {

			try {
				Client client = ClientBuilder.newClient();
				String targetResource = "http://localhost:8080/WordCountingConsumer/webapi/rest/word/";
				WordRequest request = new WordRequest();
				request.setText(word);
				Entity<WordRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
				Response response = client.target(targetResource).request(MediaType.APPLICATION_JSON).post(entity);

				String data = response.readEntity(String.class);

				logger.info("Response code " + response.getStatus());
				logger.info("Response from the server: " + data);

				return data;
				
			} catch (Exception e) {
				
				return CONSUMER_INTERUPTED_MSG;
			}

		}
	}

}
