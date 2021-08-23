package ge.nnasaridze.weatherapp.data.repositories.weather.today

import ge.nnasaridze.weatherapp.data.repositories.weather.ErrorObserver
import ge.nnasaridze.weatherapp.data.repositories.weather.WeatherRepository
import ge.nnasaridze.weatherapp.data.repositories.weather.WeatherRepository.DTOType

object WeatherHourlyRepository : WeatherRepository {
    override val type: DTOType = DTOType.HOURLY
    override var errorObserver: ErrorObserver? = null
    override var lastCapital: String = ""
}