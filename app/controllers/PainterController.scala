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
  val NewPainterId = -1
  implicit val painterWrites: Writes[Painter] = (
    (JsPath \ "painterId").write[Long] and
      (JsPath \ "firstName").write[String] and
      (JsPath \ "lastName").write[String] and
      (JsPath \ "pseudonym").write[String] and
      (JsPath \ "picture").write[String] and
      (JsPath \ "version").write[Int]) (unlift(Painter.unapply)
  )

  def find = Action { implicit request =>
    val paramsWithFirstValue = request.queryString.map { case (k, v) => k -> v(0) }
    paramsWithFirstValue.get("id") match {
      case Some(id) => {
        val painter = repo.find(id.toInt)
        Ok(Json.toJson(painter))
      }
    }
  }

  def add = Action { implicit request =>
    val paramsWithFirstValue = request.queryString.map { case (k, v) => k -> v(0) }
    val painter = repo.insert(Painter(NewPainterId, "Oscar-Claude", "Monet", "Monet", "http://www.thefamouspeople.com/profiles/images/claude-monet-2.jpg", 0))
    Ok(Json.toJson(painter))
  }
}
