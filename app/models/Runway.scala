package models

case class Runway (
                  id: Int,
                  airportRef: Int,
                  airportIdent: String,
                  surface: String,
                  leIdent: String
                  )
