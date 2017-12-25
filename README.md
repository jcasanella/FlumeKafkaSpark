# Tweeter - Flume Kafka Channel and Spark Streaming

Flume will read tweets from twitter and write to kafka Channel. Spark Streaming will show the different tweets on the spark console

## Getting Started

Before to start we must turn on kafka server

'''
$KAFKA_HOME/bin/kafka-server-start.sh ../config/server.properties
'''

Create a kafka-topic

'''
FKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 2 --topic twitter
'''

## Flume Settings

Source is twitter
Channel kafka
Sink Spark Streaming where we specify the host and port used by spark

To run flume:

'''
flume-ng agent -n twitterAgent --conf-file twitter-kafka.conf -Xmx2048m -Xms1024m
'''

To check if it works, we can run a kafka client:

'''
./kafka-console-consumer.sh --zookeeper localhost:2181 --topic twitter
'''

## Spark Streaming

Must run using the following parameters:

'''
spark-submit --master yarn-client --driver-memory 1g --executor-memory 1g --executor-cores 1 --class org.learn.di.spark.KafkaTweet KafkaSparkStream.jar localhost 3306 /user/cloudera/kafka_twitter
'''

