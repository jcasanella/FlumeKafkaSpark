package org.learn.di.spark

import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Hello world!
 *
 */
object KafkaTweet extends App {

  if (args.length < 3) {
    System.err.println(
      s"""
         |Usage: KafkaTweet <hostname> <port>
         | <hostname> flume spark sink hostname
         | <port> port for spark-streaming to connect
         | <filePath> the path to HDFS where you want to write the file containing the tweets
       """.stripMargin)

    System.exit(1)
  }

  // Get the arguments
  val Array(hostname, port, filePath) = args

  // Spark Settings
  val conf = new SparkConf().setAppName("KafkaTweet")
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc, Seconds(5))

  val stream = FlumeUtils.createPollingStream(ssc, hostname, port.toInt)
  val tweets = stream.map(flumeEvent => {
    val event = flumeEvent.event
    println(s"******** Content of the event: $event")
    println(s"******** Content of the header: $event.getHeaders")
    println(s"******** Value of the schema: $event.getSchema")
    val messageBody = new String(event.getBody.array())
    messageBody
  }
  ).print
  /*val tweets = stream.map(row => new String(row.event.getBody.array()))

  // Check if have tweets before to write
  if (tweets.count()!=0) {
      System.out.println(s"Tweet: $tweets.toString")
      tweets.saveAsTextFiles(filePath)
  }*/

  ssc.start()
  ssc.awaitTermination()
}
