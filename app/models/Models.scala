package models

import anorm.Macro
/**
  * Created by Prashant S Khanwale @ Suveda LLC  on 5/7/16.
  */

case class Response[A](error:Option[Feedback], data:Option[A])
case class Feedback(key:String, cause:Option[String])
case class Painter(painterId: Long,  firstName: String, lastName: String, pseudonym: String,  picture: String, version:Int)
object Painter {
  def parser = Macro.parser[Painter] ("painter_Id", "first_Name", "last_Name", "pseudonym", "picture", "version")
  implicit def  painterToMapEntry (p:Painter) : (Long, Painter) = {
    p.painterId -> p
  }
  implicit def  painterToId (p:Painter) : Long = {
    p.painterId
  }
}
