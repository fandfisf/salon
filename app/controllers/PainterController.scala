package controllers

import javax.inject._

import akka.actor.ActorSystem
import models.{Feedback, Painter}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc._
import repositories.PainterRepository

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */

@Singleton
class PainterController @Inject()(val repo: PainterRepository
                                  , ws: WSClient
                                  , actorSystem: ActorSystem)
                                 (implicit exec: ExecutionContext) extends Controller with FeedbackProvider{
  val NewPainterId = -1
  implicit val painterWrites = Json.writes[Painter]
//  implicit val painterWrites: Writes[Painter] = (
//    (JsPath \ "painterId").write[Long] and
//      (JsPath \ "firstName").write[String] and
//      (JsPath \ "lastName").write[String] and
//      (JsPath \ "pseudonym").write[String] and
//      (JsPath \ "picture").write[String] and
//      (JsPath \ "version").write[Int]) (unlift(Painter.unapply)
//  )
  def find(id: Option[Long]) = Action { implicit request =>
    id match {
      case Some(id) => {
        val painter = repo.find(id)
        Ok(Json.toJson(painter))
      }
      case _ => Ok(Json.toJson(Feedback("NO_ID_SUPPLIED", None)))
    }
  }

  def add = Action { implicit request =>
    val painter = repo.insert(Painter(NewPainterId, "Oscar-Claude", "Monet", "Monet", "http://www.thefamouspeople.com/profiles/images/claude-monet-2.jpg", 0))
    Ok(Json.toJson(painter))
  }
}
