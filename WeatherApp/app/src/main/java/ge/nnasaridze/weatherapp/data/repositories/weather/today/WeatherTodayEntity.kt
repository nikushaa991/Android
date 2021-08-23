package ge.nnasaridze.weatherapp.data.repositories.weather.today

import ge.nnasaridze.weatherapp.data.repositories.weather.WeatherDTO

class WeatherTodayEntity(data: WeatherDTO) {
    val main = data.main
    val weather = data.weather
    val dt = data.dt
    val sys = data.sys
}

