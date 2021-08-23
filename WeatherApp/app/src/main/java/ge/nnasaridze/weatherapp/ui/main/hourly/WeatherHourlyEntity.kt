package ge.nnasaridze.weatherapp.ui.main.hourly

import ge.nnasaridze.weatherapp.data.repositories.weather.Main
import ge.nnasaridze.weatherapp.data.repositories.weather.Weather
import ge.nnasaridze.weatherapp.data.repositories.weather.WeatherDTO

class WeatherHourlyEntity(data: WeatherDTO) {

    val list = formatHourlyList(data)

    data class FormattedHourData(
        val dtTxt: String,
        val main: Main,
        val weather: List<Weather>,
    )

    private fun formatHourlyList(data: WeatherDTO): List<FormattedHourData> {
        val res = arrayListOf<FormattedHourData>()
        data.list.forEach {
            val date = formatDate(it.dtTxt)
            res.add(FormattedHourData(date, it.main, it.weather))
        }
        return res
    }

    private fun formatDate(dtTxt: String): String {
        val hour = dtTxt.subSequence(11, 13).toString().toInt()
        val day = dtTxt.subSequence(8, 10).toString()
        val month = dtTxt.subSequence(5, 7).toString().toInt()
        val period = if (hour in 0..11) "AM" else "PM"
        val hourStr = (if (hour % 12 < 10) "0" else "") + (hour % 12).toString()
        return "$hourStr $period $day ${months[month]}"
    }

    companion object {
        val months = mapOf(
            1 to "Jan",
            2 to "Feb",
            3 to "Mar",
            4 to "Apr",
            5 to "May",
            6 to "Jun",
            7 to "Jul",
            8 to "Aug",
            9 to "Sep",
            10 to "Oct",
            11 to "Nov",
            12 to "Dec"
        )
    }
}