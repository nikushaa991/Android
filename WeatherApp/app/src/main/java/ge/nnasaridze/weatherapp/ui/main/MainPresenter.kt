package ge.nnasaridze.weatherapp.ui.main

import ge.nnasaridze.weatherapp.data.repositories.country.Country
import ge.nnasaridze.weatherapp.data.repositories.country.CountryRepository
import ge.nnasaridze.weatherapp.data.repositories.country.CountryRepositoryImpl
import ge.nnasaridze.weatherapp.data.repositories.country.Observer
import ge.nnasaridze.weatherapp.data.repositories.weather.ErrorObserver
import ge.nnasaridze.weatherapp.data.repositories.weather.WeatherDTO
import ge.nnasaridze.weatherapp.data.repositories.weather.WeatherRepository
import ge.nnasaridze.weatherapp.data.repositories.weather.today.WeatherTodayEntity
import ge.nnasaridze.weatherapp.data.repositories.weather.today.WeatherTodayRepository

interface MainPresenter {
    fun onCountryTapped(country: Country)
    fun onHourlyTapped()
    fun onTodayTapped()
}

class MainPresenterImpl(private val view: MainView) : MainPresenter, Observer, ErrorObserver {

    private val countryRepository: CountryRepository = CountryRepositoryImpl
    private val weatherRepository: WeatherRepository = WeatherTodayRepository

    init {
        countryRepository.subscribe(this)
        weatherRepository.registerErrorObserver(this)
    }

    override fun onCountryTapped(country: Country) {
        countryRepository.setCurrentCountry(country)
    }

    override fun onHourlyTapped() {
        view.setHourlyFragment()
    }

    override fun onTodayTapped() {
        view.setTodayFragment()
    }

    override fun observableChanged() {
        weatherRepository.fetchData(countryRepository.getCurrentCountryCapital(), ::handler)
    }

    private fun handler(data: WeatherDTO?) {
        data?.let {
            val todayData = WeatherTodayEntity(it)

            view.setCapitalName(countryRepository.getCurrentCountryCapital())
            if (todayData.dt > todayData.sys.sunrise && todayData.dt < todayData.sys.sunset)
                view.setDayBackground()
            else
                view.setNightBackground()
        }
    }

    override fun handleError() {
        view.showError("Unable to retrieve data")
    }
}