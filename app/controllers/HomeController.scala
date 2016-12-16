package controllers

import javax.inject._

import models.{Airport, Country, Runway}
import play.api.cache.Cached
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import services.{Airports, Countries, Reports}

import scala.concurrent.Future


@Singleton
class HomeController @Inject()(cached: Cached, reportService: Reports, countries: Countries, airports: Airports) extends Controller {

  def index = cached("index") {
    Action {
      Ok(views.html.index("Lunatech code assesment demo"))
    }
  }

  def query(maybeQuery: Option[String]) = cached(s"query$maybeQuery") {
    Action.async {
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
  }

  def reports = cached("reports") {
    Action.async { req =>
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
}


