package ge.nnasaridze.weatherapp.ui.main.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ge.nnasaridze.weatherapp.R
import ge.nnasaridze.weatherapp.data.repositories.weather.today.WeatherTodayEntity
import ge.nnasaridze.weatherapp.databinding.FragmentTodayBinding
import ge.nnasaridze.weatherapp.ui.main.Utils

interface TodayView {
    fun setTodayData(today: WeatherTodayEntity)
}

class MainTodayFragment : TodayView, Fragment() {

    private lateinit var binding: FragmentTodayBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(inflater, container, false)
        TodayPresenterImpl(this)
        return binding.root
    }

    override fun setTodayData(today: WeatherTodayEntity) {
        Utils.loadUrlImage(requireContext(), binding.weatherImage, today.weather[0].icon)

        binding.apply {
            temperatureText.text = activity?.getString(R.string.temperature_value, today.main.temp)
            statusText.text = today.weather[0].description.uppercase()
            temperatureValue.text = activity?.getString(R.string.temperature_value, today.main.temp)
            feelslikeValue.text =
                activity?.getString(R.string.temperature_value, today.main.feelsLike)
            humidityValue.text = activity?.getString(R.string.humidity_value, today.main.humidity)
            pressureValue.text = today.main.pressure.toString()
        }
    }
}