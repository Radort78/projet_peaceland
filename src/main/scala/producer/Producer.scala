package producer

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer
import drone.{Drone, Main}
import play.api.libs.json.{JsValue, Json}

import java.util.Properties

object Producer {
  def main(args: Array[String]): Unit = { //HEY //Hello
    val props: Properties = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])

    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)

    val key = "akey"

//    (0 to 100).foreach(x => Json.stringify(Main.generateReport()))

    val value = Json.stringify(Main.generateReport())

    val record = new ProducerRecord[String, String]("person", value)

    producer.send(record, (recordMetaData: RecordMetadata, exception: Exception) => {
      println("Done")
      if (exception != null) {
        exception.printStackTrace()
      } else {
        println(s"Metadata about the sent record : $recordMetaData")
      }
      Thread.sleep(10000)
    })

    producer.close()
  }
}
