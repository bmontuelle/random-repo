package controllers

import javax.inject._
import models.{Country, Runway, Airport}
import play.api._
import play.api.mvc._
import services.{Countries, Airports, Reports}

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(reportService: Reports, countries: Countries, airports: Airports) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Lunatech code assesment demo"))
  }

  def query(maybeQuery: Option[String]) = Action.async {
    val fap: Future[Vector[(Airport, Vector[Runway])]] = maybeQuery match {
      case None => Future.successful(Vector[(Airport, Vector[Runway])]())
      case Some(q) => {
        countries.search(q).flatMap((maybeCountry: Option[Country]) => {
          maybeCountry.map(country => airports.byCountry(country.code))
            .getOrElse(Future.successful(Vector[(Airport, Vector[Runway])]()))
        })
      }
    }

    fap.map(ap => Ok(views.html.query(maybeQuery, ap)))
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
