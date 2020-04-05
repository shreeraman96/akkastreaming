package com.beovo.ingestor.models

import spray.json.{DefaultJsonProtocol, JsonFormat}


final case class FoodReview(id:Int,name:String,rating:Int,cuisine:String)

object MyJsonProtocol extends DefaultJsonProtocol {
    implicit val foodreviewFormat:JsonFormat[FoodReview] = jsonFormat4(FoodReview)
}


