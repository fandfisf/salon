package controllers

import javax.inject._

import akka.actor.ActorSystem
import models.Painter
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Json, Writes}
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
                                  (implicit exec: ExecutionContext) extends Controller {
  implicit val painterWrites: Writes[Painter] = (
    (JsPath \ "painterId").write[Int] and
    (JsPath \ "firstName").write[String] and
      (JsPath \ "lastName").write[String] and
      (JsPath \ "pseudonym").write[String] and
      (JsPath \ "picture").write[String])(unlift(Painter.unapply)
  )

  def index = Action { implicit request =>
    val paramsWithFirstValue = request.queryString.map { case (k, v) => k -> v(0) }
    paramsWithFirstValue.get("id") match {
      case Some(id) => {
        val painter = repo.find(id.toInt)
        Ok(Json.toJson(painter))
      }
    }
  }

}
