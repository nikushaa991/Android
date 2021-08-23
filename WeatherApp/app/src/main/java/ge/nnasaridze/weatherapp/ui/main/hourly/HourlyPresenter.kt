package ge.nnasaridze.weatherapp.ui.main.hourly

import ge.nnasaridze.weatherapp.data.repositories.country.CountryRepository
import ge.nnasaridze.weatherapp.data.repositories.country.CountryRepositoryImpl
import ge.nnasaridze.weatherapp.data.repositories.country.Observer
import ge.nnasaridze.weatherapp.data.repositories.weather.WeatherDTO
import ge.nnasaridze.weatherapp.data.repositories.weather.WeatherRepository
import ge.nnasaridze.weatherapp.data.repositories.weather.today.WeatherHourlyRepository
import ge.nnasaridze.weatherapp.ui.main.hourly.WeatherHourlyEntity.FormattedHourData

interface HourlyPresenter {
    fun getItemCount(): Int
    fun getItemAtPosition(position: Int): FormattedHourData
    fun registerView(view: IView)
}

class HourlyPresenterImpl : HourlyPresenter, Observer {
    private val countryRepository: CountryRepository = CountryRepositoryImpl
    private val weatherRepository: WeatherRepository = WeatherHourlyRepository
    private lateinit var weatherHourlyData: WeatherHourlyEntity
    private lateinit var view: IView

    init {
        countryRepository.subscribe(this)
    }

    override fun getItemCount(): Int {
        if (!::weatherHourlyData.isInitialized) return 0
        return weatherHourlyData.list.size
    }

    override fun getItemAtPosition(position: Int): FormattedHourData {
        return weatherHourlyData.list[position]
    }

    override fun registerView(view: IView) {
        this.view = view
    }

    override fun observableChanged() {
        weatherRepository.fetchData(countryRepository.getCurrentCountryCapital(), ::handler)
    }

    private fun handler(data: WeatherDTO?) {
        data?.let {
            weatherHourlyData = WeatherHourlyEntity(it)
            view.updateData()
        }
    }
}