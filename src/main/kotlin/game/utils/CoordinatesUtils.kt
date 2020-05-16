package game.utils

import game.map.Coordinates
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

/**
 * Using Haversine formula
 */
fun calculateDistanceBetweenTwoPoints(
    sourceCordinates: Coordinates,
    destination: Coordinates,
    planetRadius: Double
): Double {
    val sourceLongitude = toRadians(sourceCordinates.longitude)
    val destinationLongitude = toRadians(destination.longitude)
    val sourceLatitude = toRadians(sourceCordinates.latitude)
    val destinationLatitude = toRadians(destination.latitude)

    val distanceLongitude = destinationLongitude - sourceLongitude
    val distanceLatitude = destinationLatitude - sourceLatitude

    val a = (sin(distanceLatitude / 2).pow(2.0)
            + (cos(sourceLatitude) * cos(destinationLatitude)
            * sin(distanceLongitude / 2).pow(2.0)))

    return 2 * planetRadius * kotlin.math.asin(kotlin.math.sqrt(a))
}