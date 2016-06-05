package controllers

import javax.inject._

import akka.actor.ActorSystem
import models.Painter
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

  implicit def painterWrites = Json.writes[Painter]

  implicit def painterReads = Json.reads[Painter]

  def find(id: Long) = Action { implicit request =>
    val painter = repo.find(id)
    Ok(Json.toJson(painter))
  }

  def add = Action { implicit request =>
    val painter = repo.insert(Painter(NewPainterId, "Oscar-Claude", "Monet", "Monet", None, None, "http://www.thefamouspeople.com/profiles/images/claude-monet-2.jpg", 0))
    Ok(Json.toJson(painter))
  }

  def delete(id: Long) = Action { implicit request =>
    val painter = repo.insert(Painter(NewPainterId, "Oscar-Claude", "Monet", "Monet", None, None, "http://www.thefamouspeople.com/profiles/images/claude-monet-2.jpg", 0))
    Ok(Json.toJson(painter))
  }

  def update = Action { implicit request =>
    val painter = repo.insert(Painter(NewPainterId, "Oscar-Claude", "Monet", "Monet", None, None, "http://www.thefamouspeople.com/profiles/images/claude-monet-2.jpg", 0))
    Ok(Json.toJson(painter))
  }
}
