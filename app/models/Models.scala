package models

import java.util.Date

/**
  * Created by Prashant S Khanwale @ Suveda LLC  on 5/7/16.
  */

case class Feedback(key:String, cause:Option[String])
case class Person (name:String)

case class Painter(
                    painterId: Long,
                    firstName: String,
                    lastName: String,
                    pseudonym: String,
                    birthDate: Option[Date],
                    dateOfDeath: Option[Date],
                    picture: String,
                    version: Int)
object Painter {

  implicit def  painterToMapEntry (p:Painter) : (Long, Painter) = {
    p.painterId -> p
  }
  implicit def  painterToId (p:Painter) : Long = {
    p.painterId
  }
}
case class Response[A](error:Option[Feedback], data:Option[A])
