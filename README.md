# WordCountApp
Distributed system for Producing and Consuming words through a Rest API to a Kafka cluster

To run:

1. Download the appropriate binary build from https://kafka.apache.org/downloads and explode into a folder.
2. Follow the Quickstart(https://kafka.apache.org/quickstart) instructions. Be sure to start Zookeeper before starting Kafka.
3. Deploy all three war files located in each applications target folder to a tomcat 9 server.
  - If you don't have a tomcat server running you can get a copy from here https://tomcat.apache.org/tomcat-9.0-doc/setup.html
4. Start tomcat, open a browser and type the following url to start the Word Counting App.
  - http://localhost:8080/WordCountingApp/

