package consumer
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import play.api.libs.json.JsValue
import collection.JavaConverters._
import java.time.Duration
import java.util.Properties

object Consumer {
  def main(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "myconsumergroup")

    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(List("atopic", "my_topic_1", "my_topic_2", "person").asJava)

    val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMillis(1000))
    println(records.count())
    records.asScala.foreach(
      record => println(s"offset = ${record.offset()}, key = ${record.key()}, value = ${record.value()}")
    )

    consumer.commitSync()

    //    consumer.commitAsync((offsets: Map[TopicPartition, OffsetAndMetadata], exception: Exception) => {
    //      println("toto")
    //    })
  }
}
