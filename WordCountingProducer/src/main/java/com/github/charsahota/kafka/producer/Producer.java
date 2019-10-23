package com.github.charsahota.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Client that publishes records to the Kafka cluster.
 * 
 * @author Char Sahota
 * @date Oct. 22, 2019
 *
 */
public class Producer {
    private final KafkaProducer<String, String> producer;
    private final String topic;
    private final Boolean isAsync;

    /**
     * Initialize the client with property settings.
     * 
     * @param topic
     * @param isAsync
     */
    public Producer(String topic, Boolean isAsync) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_BOOTSTRAP_SERVER);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaProperties.CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
        this.topic = topic;
        this.isAsync = isAsync;
    }
    
    /**
     * Method to send each individual word to the kafka cluster.
     * Can be set for asynchronous sends.
     * 
     * @param messageStr
     */
    public void sendMessage(String messageStr) {
    	
        long startTime = System.currentTimeMillis();
        if (isAsync) { // Send asynchronously
        	
            producer.send(new ProducerRecord<>(topic,
                messageStr), new ProducerCallBack(startTime, topic, messageStr));
            
        } else { // Send synchronously
        	
            try {
            	
                producer.send(new ProducerRecord<>(topic,
                    messageStr)).get();
                System.out.println("Sent message: (" + topic + ", " + messageStr + ")");
                
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    /**
     * Close the Producer once all words have been submitted to the Kafka Cluster.
     */
    public void close() {
        producer.flush();
        producer.close();  	
    }
}

/**
 * A callback class to implement execution of code when the request is complete. 
 * Executes in the background I/O thread.
 * 
 * @author Char Sahota
 * @date Oct. 22, 2019
 *
 */
class ProducerCallBack implements Callback {

    private final long startTime;
    private final String key;
    private final String message;

    /**
     * Initialize CallBack class
     * 
     * @param startTime
     * @param key
     * @param message
     */
    public ProducerCallBack(long startTime, String key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

    /**
     * Simply task to write to the console once completed.
     */
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (metadata != null) {
            System.out.println(
                "message(" + key + ", " + message + ") sent to partition(" + metadata.partition() +
                    "), " +
                    "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
        } else {
            exception.printStackTrace();
        }
    }
}
