package models

import anorm.Macro

/**
  * Created by Prashant S Khanwale @ Suveda LLC  on 5/7/16.
  */
case class Painter(painterId: Int,  firstName: String, lastName: String, pseudonym: String,  picture: String){
}
object Painter {
  def parser = Macro.parser[Painter] ("painter_Id", "first_Name", "last_Name", "pseudonym", "picture")
}
