package controllers

import javax.inject._

import akka.actor.ActorSystem
import play.api.libs.json.{JsPath, Json, Writes}
import play.api.libs.ws.WSClient
import play.api.mvc._
import repositories.{Employee, EmployeeRepository}
import play.api.libs.functional.syntax.unlift
import play.api.libs.functional.syntax.toFunctionalBuilderOps

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */

@Singleton
class EmployeeController @Inject()(val repo: EmployeeRepository
                                   , ws: WSClient
                                   , actorSystem: ActorSystem)
                                  (implicit exec: ExecutionContext) extends Controller {
  implicit val employeeWrites: Writes[Employee] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "firstName").write[String] and
      (JsPath \ "lastName").write[String]) (unlift(Employee.unapply))


  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    val employees = repo.find()
    Ok(Json.toJson(employees))
  }

}
