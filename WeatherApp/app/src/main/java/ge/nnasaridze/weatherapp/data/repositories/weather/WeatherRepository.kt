package ge.nnasaridze.weatherapp.data.repositories.weather

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherRepository {

    val type: DTOType
    var errorObserver: ErrorObserver?
    var lastCapital: String

    fun fetchData(capitalName: String, handler: (response: WeatherDTO?) -> Unit) {
        synchronized(lastCapital) {
            lastCapital = capitalName
        }
        service.getWeather(type.query, capitalName, APP_ID).enqueue(object :
            Callback<WeatherDTO> {
            override fun onResponse(
                call: Call<WeatherDTO>,
                response: Response<WeatherDTO>
            ) {
                synchronized(lastCapital) {
                    if (lastCapital == capitalName)
                        handler(response.body())
                }
            }

            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                errorObserver?.handleError()
            }
        })
    }

    fun registerErrorObserver(errorObserver: ErrorObserver) {
        this.errorObserver = errorObserver
    }

    companion object {
        private const val BASE_URL = "http://api.openweathermap.org/"
        private const val APP_ID = "e7e7d5d2da4aee3dcb44529c9c69f31a"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        private val service = retrofit.create(WeatherAPI::class.java)
    }

    interface WeatherAPI {
        @GET("data/2.5/{type}?")
        fun getWeather(
            @Path("type") callType: String,
            @Query("q") capitalName: String,
            @Query("appid") appid: String,
            @Query("units") units: String = "metric"
        ): Call<WeatherDTO>
    }

    enum class DTOType(val query: String) {
        TODAY("weather"),
        HOURLY("forecast");
    }

}

interface ErrorObserver {
    fun handleError()
}