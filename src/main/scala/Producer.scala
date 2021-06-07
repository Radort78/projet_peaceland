import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer

object Producer {
  def main(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])

    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)

    val key = "akey"
    val value = "adata"

    val record = new ProducerRecord[String, String]("atopic", key, value)
    producer.send(record, (recordMetaData: RecordMetadata, exception: Exception) => {
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
