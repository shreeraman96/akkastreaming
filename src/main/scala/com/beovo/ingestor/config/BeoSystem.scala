package com.beovo.ingestor.config

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait BeoSystem extends App {

  implicit val system = ActorSystem("trial")
  implicit val mat = ActorMaterializer()

}
