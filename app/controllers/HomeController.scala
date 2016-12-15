package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import services.Reports

import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(reportService: Reports) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Lunatech code assesment demo"))
  }

  def reports = Action.async { req =>
    for {
      top10Countries <- reportService.top10Countries
      bottom10Countries <- reportService.bottom10Countries
      typeOfRunways <- reportService.typeOfRunways
      top10RunwaysIdent <- reportService.top10RunwaysIdent
    } yield {
      Ok(views.html.reports(top10Countries, bottom10Countries, typeOfRunways, top10RunwaysIdent))
    }
  }
}
