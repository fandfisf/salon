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
  implicit val categoryWrites: Writes[Painter] = (
    (JsPath \ "painterId").write[Int] and
    (JsPath \ "firstName").write[String] and
      (JsPath \ "lastName").write[String] and
      (JsPath \ "pseudonym").write[String] and
      (JsPath \ "picture").write[String])(unlift(Painter.unapply)
  )


  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    val category = repo.find(1)
    Ok(Json.toJson(category))
  }

}
