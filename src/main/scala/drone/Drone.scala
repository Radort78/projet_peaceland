package drone

import play.api.libs.json._

import scala.util.Random;

case class Point(lat: Double, lon: Double)

case class Citizen(name: String, peacescore: Int)

case class Report(id: String, location: Point, citizen: Citizen, words: List[String])

class Drone(id: String, location: Point)
{
  def createDummyReport(): Report = {
    val c = new Citizen("John Doe", Random.nextInt(100))
    val l = List("DummyWord1", "DummyWord2", "DummyWord3")
    new Report(this.id, this.location, c, l)
  }
}

object Main {

  def generateReport():JsValue = {
    val id = Random.alphanumeric.filter(!_.isDigit).take(10).mkString
    val point = new Point(Random.between(-10.0, 10.0), Random.between(-10.0, 10.0))
    val d = new Drone(id, point)
    val report = d.createDummyReport()

    implicit val citizenWrites: OWrites[Citizen] = Json.writes[Citizen]
    implicit val pointWrites: OWrites[Point] = Json.writes[Point]
    implicit val reportWrites: OWrites[Report] = Json.writes[Report]
    val reportJson: JsValue = Json.toJson(report)

    reportJson
  }

  def main(args: Array[String]): Unit = {
    val reportJson: JsValue = generateReport()

    reportJson

//    val queueName = "amqp-conn-it-spec-simple-queue-" + System.currentTimeMillis()
//    val queueDeclaration = QueueDeclaration(queueName)
//    val connectionProvider = AmqpLocalConnectionProvider

//    val settings = AmqpWriteSettings(connectionProvider)
//      .withRoutingKey(queueName)
//      .withDeclaration(queueDeclaration)
//      .withBufferSize(10)
//      .withConfirmationTimeout(200)//
//
//    val amqpFlow: Flow[WriteMessage, WriteResult, Future[Done]] =
//      AmqpFlow.withConfirm(settings)
//
//    val input = Vector("one", "two", "three", "four", "five")
//    val result: Future[Seq[WriteResult]] =
//      Source(input)
//        .map(message => WriteMessage(ByteString(message)))
//        .via(amqpFlow)
//        .runWith(Sink.seq)

  }
}