
package com.github.charsahota.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

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
 */
public class Consumer {
	
	private final KafkaConsumer<String, String> consumer;
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
	 * Returns a html formated string for display in the browser.
	 * 
	 * @param word
	 * @return
	 */
	public String retreiveWordRecords(String word) {
		
		List<ConsumerRecord<String, String>> fullList = new ArrayList<ConsumerRecord<String, String>>();
		
		consumer.subscribe(Arrays.asList(topic));
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));		
		records.spliterator().forEachRemaining(fullList::add);
		
		List<ConsumerRecord<String, String>> sameWords = fullList.stream()
				.filter(r -> r.value().equalsIgnoreCase(word) )
				.collect(Collectors.toList());
			
		StringBuffer words = new StringBuffer();
		
		if(sameWords.size() > 0) {

			ConsumerRecord<String, String> firstRecord = sameWords.get(0);
			ConsumerRecord<String, String> lastRecord = sameWords.get(sameWords.size()-1);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			LocalDateTime firstDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(firstRecord.timestamp()), ZoneId.systemDefault());
			LocalDateTime lastDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastRecord.timestamp()), ZoneId.systemDefault());
			
			words.append("Word [<b>").append(word).append("</b>]");
			words.append("</br>&nbsp;&nbsp; Number of records: ").append(sameWords.size());
			words.append("</br>&nbsp;&nbsp; First Submit Time: ").append(firstDate.format(formatter));
			words.append("</br>&nbsp;&nbsp; Last Submit Time:  ").append(lastDate.format(formatter));
			
		} else {
			return "Word was not found in previously entered text.";
		}
		
		for (ConsumerRecord<String, String> record : sameWords) {
			System.out.println("Received message: (" + record.offset() + ", " + record.value() + ")");
		}
		
		consumer.unsubscribe();
		return words.toString();
	}
}
