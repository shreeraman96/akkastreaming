package com.beovo.ingestor.main

import akka.stream.alpakka.slick.scaladsl._
import scala.concurrent.{Await, Future}
import akka.Done
import org.bson.codecs.configuration.CodecRegistries._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import com.mongodb.reactivestreams.client.MongoClients
import akka.stream.alpakka.mongodb.scaladsl.MongoSink
import com.beovo.ingestor.config.BeoSystem
import com.beovo.ingestor.models.FoodReview
import com.beovo.ingestor.utils.Utilities
import akka.kafka.scaladsl.Producer
import spray.json._
import org.apache.kafka.clients.producer.ProducerRecord
import com.beovo.ingestor.utils.Constants
import DefaultJsonProtocol._
import com.beovo.ingestor.models.MyJsonProtocol

object Ingestor extends BeoSystem {

  implicit val session:SlickSession = Utilities.getSlickSession("slick-mysql")
  system.registerOnTermination(() => session.close())

  import session.profile.api._

  //data model for the data from MySQL
  class Reviews(tag: Tag) extends Table[(Int, String,Int,String)](tag, "akkatrial") {
    def id = column[Int]("ID")
    def name = column[String]("NAME")
    def rating = column[Int]("RATING")
    def cuisine = column[String]("CUISINE")
    def * = (id, name,rating,cuisine)
  }

  //building mongo client
  val codecRegistry = fromRegistries(fromProviders(classOf[FoodReview]),DEFAULT_CODEC_REGISTRY)
  private val client = MongoClients.create(Constants.MONGO_URL)
  private val db = client.getDatabase("akkatrial").withCodecRegistry(codecRegistry)
  private val reviewsColl = db
    .getCollection("review_test", classOf[FoodReview])
    .withCodecRegistry(codecRegistry)

  //building kakfa producer client
  private val producerSettings = Utilities.getKafkaProducerSetting(system)


  //streaming from MySQL to MongoDB
  val done: Future[Done] =
    Slick.source(TableQuery[Reviews].result)
         .map( reviews => FoodReview(id = reviews._1,name = reviews._2,rating = reviews._3,cuisine = reviews._4))
         .runWith(MongoSink.insertOne[FoodReview](reviewsColl))

  //streaming from MySQL to Kafka
  val done_2:Future[Done] = {
    Slick.source(TableQuery[Reviews].result)
      .map{
        reviews =>
          val review = FoodReview(id = reviews._1, name = reviews._2,rating = reviews._3,cuisine = reviews._4)
          import MyJsonProtocol._
          new ProducerRecord[String,String](Constants.TOPIC,review.toJson.compactPrint)
      }.runWith(Producer.plainSink(producerSettings))
  }


  implicit val ec = system.dispatcher
  done.onComplete{_ => system.terminate()}

}
