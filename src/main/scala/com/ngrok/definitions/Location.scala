package com.ngrok.definitions

import io.circe.syntax._

/**
 * A class encapsulating the [[Location]] resource.
 *
 * @constructor create a new Location.
 * @param countryCode ISO country code
 * @param latitude geographical latitude
 * @param longitude geographical longitude
 * @param latLongRadiusKm accuracy radius of the geographical coordinates
 */
final case class Location(
  countryCode: Option[String] = None,
  latitude: Option[Double] = None,
  longitude: Option[Double] = None,
  latLongRadiusKm: Option[Long] = None,
)

object Location {
  implicit val encodeLocation: io.circe.Encoder[Location] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      value.countryCode.map(_.asJson).map(("country_code", _)),
      value.latitude.map(_.asJson).map(("latitude", _)),
      value.longitude.map(_.asJson).map(("longitude", _)),
      value.latLongRadiusKm.map(_.asJson).map(("lat_long_radius_km", _)),
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeLocation: io.circe.Decoder[Location] = (c: io.circe.HCursor) => for {
    countryCode <- c.downField("country_code").as[Option[String]]
    latitude <- c.downField("latitude").as[Option[Double]]
    longitude <- c.downField("longitude").as[Option[Double]]
    latLongRadiusKm <- c.downField("lat_long_radius_km").as[Option[Long]]
  } yield Location(
    countryCode,
    latitude,
    longitude,
    latLongRadiusKm,
  )
}
