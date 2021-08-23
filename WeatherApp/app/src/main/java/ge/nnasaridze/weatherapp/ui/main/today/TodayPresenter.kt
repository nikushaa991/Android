package ge.nnasaridze.weatherapp.ui.main.today

import ge.nnasaridze.weatherapp.data.repositories.country.CountryRepository
import ge.nnasaridze.weatherapp.data.repositories.country.CountryRepositoryImpl
import ge.nnasaridze.weatherapp.data.repositories.country.Observer
import ge.nnasaridze.weatherapp.data.repositories.weather.WeatherDTO
import ge.nnasaridze.weatherapp.data.repositories.weather.WeatherRepository
import ge.nnasaridze.weatherapp.data.repositories.weather.today.WeatherTodayEntity
import ge.nnasaridze.weatherapp.data.repositories.weather.today.WeatherTodayRepository

interface TodayPresenter

class TodayPresenterImpl(private val view: TodayView) : TodayPresenter, Observer {

    private val countryRepository: CountryRepository = CountryRepositoryImpl
    private val weatherRepository: WeatherRepository = WeatherTodayRepository

    init {
        countryRepository.subscribe(this)
    }

    override fun observableChanged() {
        weatherRepository.fetchData(countryRepository.getCurrentCountryCapital(), ::handler)
    }

    private fun handler(data: WeatherDTO?) {
        data?.let {
            val todayData = WeatherTodayEntity(data)
            view.setTodayData(todayData)
        }
    }
}