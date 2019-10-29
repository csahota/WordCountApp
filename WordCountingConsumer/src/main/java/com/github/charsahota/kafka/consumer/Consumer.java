
package com.github.charsahota.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.github.charsahota.kafka.model.WordResponse;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * KafkaConsumer Client for consuming records from a Kafka cluster 
 * 
 * @author Char Sahota
 * @date Oct. 22, 2019
 * 
 * Edit: Oct. 26, 2019
 * Method: retreiveWordRecords
 * 1. Moved the unscubscibe method and introduced the close method to prevent memory leaks and multiple instances.  
 * 2. Introduced the WordResponse object for creating a JSON string return.
 *
 */
public class Consumer {
	
	private static KafkaConsumer<String, String> consumer;
	private final String topic;

	/**
	 * Constructor sets the topic and property variables required to initialize.
	 * 
	 * @param topic
	 */
	public Consumer(String topic) {

		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_BOOTSTRAP_SERVER);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "ConsumerId");

		consumer = new KafkaConsumer<>(props);
		this.topic = topic;

	}

	/**
	 * Pulling the records for this static topic. 
	 * The method filters the records for and list that contains the entered word.
	 * Uses the ConsumerRecord objects timestamp to evaluate the date saved to the Kafka Cluster.
	 * Returns a WordResponse object to form a JSON string response.
	 * 
	 * @param word
	 * @return
	 */
	public WordResponse retreiveWordRecords(String word) {
		
		WordResponse response = new WordResponse(word);
		List<ConsumerRecord<String, String>> fullList = new ArrayList<ConsumerRecord<String, String>>();
		
		consumer.subscribe(Arrays.asList(topic));
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));		
		records.spliterator().forEachRemaining(fullList::add);
		consumer.unsubscribe();
		consumer.close();
		
		List<ConsumerRecord<String, String>> sameWords = fullList.stream()
				.filter(r -> r.value().equalsIgnoreCase(word) )
				.collect(Collectors.toList());
		
		if(sameWords.size() > 0) {

			ConsumerRecord<String, String> firstRecord = sameWords.get(0);
			ConsumerRecord<String, String> lastRecord = sameWords.get(sameWords.size()-1);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
			
			LocalDateTime firstDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(firstRecord.timestamp()), ZoneId.systemDefault());
			LocalDateTime lastDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastRecord.timestamp()), ZoneId.systemDefault());
			
			response.setWordCount(String.valueOf(sameWords.size()));
			response.setFirstDate(firstDate.format(formatter));
			response.setLastDate(lastDate.format(formatter));
			response.setStatus("SUCCESS");
			
		} else {
			response.setStatus("FAILURE");
			response.setMessage("Word was not found in previously entered text.");
		}
		
		for (ConsumerRecord<String, String> record : sameWords) {
			System.out.println("Received message: (" + record.offset() + ", " + record.value() + ")");
		}
		
		return response;
	}
	
}
