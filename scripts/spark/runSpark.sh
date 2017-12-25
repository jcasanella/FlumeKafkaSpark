spark-submit --master yarn-client --driver-memory 1g --executor-memory 1g --executor-cores 1 --class org.learn.di.spark.KafkaTweet KafkaSparkStream.jar localhost 3306 /user/cloudera/kafka_twitter
