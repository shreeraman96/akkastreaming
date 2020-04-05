package com.beovo.ingestor.utils

import akka.actor.ActorSystem
import akka.stream.alpakka.slick.scaladsl.SlickSession
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import org.apache.kafka.common.serialization.StringSerializer
import akka.kafka.ProducerSettings
import akka.stream.scaladsl._
import com.beovo.ingestor.utils.Constants

object Utilities {

  def getSlickSession(config_path:String):SlickSession = {

    val databaseConfig = DatabaseConfig.forConfig[JdbcProfile](config_path)
    val session = SlickSession.forConfig(databaseConfig)
    session

  }

  def getKafkaProducerSetting(system: ActorSystem) = {
    val config = system.settings.config.getConfig("akka.kafka.producer")
    val producerSettings =
      ProducerSettings(config, new StringSerializer, new StringSerializer)
        .withBootstrapServers(Constants.KAFKA_BOOTSTRAP_SERVERS)
    producerSettings

  }

}
