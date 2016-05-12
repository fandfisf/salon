package controllers

import models.Feedback
import play.api.libs.json.Json

/**
  * Created by Prashant S Khanwale @ Suveda LLC  on 5/11/16.
  */
trait FeedbackProvider {
  implicit val feedbackWrites = Json.writes[Feedback]
}
object FeedbackProvider extends FeedbackProvider {

}
//trait ResponseProvider [A]{
//  implicit val responseWrites= Json.writes[Response[A]]
//}
//object ResponseProvider extends ResponseProvider{
//
//}