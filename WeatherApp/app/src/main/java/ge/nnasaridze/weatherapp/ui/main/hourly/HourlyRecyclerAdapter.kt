package ge.nnasaridze.weatherapp.ui.main.hourly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.nnasaridze.weatherapp.R
import ge.nnasaridze.weatherapp.databinding.RecyclerWeatherItemBinding
import ge.nnasaridze.weatherapp.ui.main.Utils
import ge.nnasaridze.weatherapp.ui.main.hourly.WeatherHourlyEntity.FormattedHourData

class MainHourlyRecyclerAdapter(private val presenter: HourlyPresenter) : IView,
    RecyclerView.Adapter<WeatherItemViewHolder>() {

    init {
        presenter.registerView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherItemViewHolder =
        WeatherItemViewHolder(
            RecyclerWeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WeatherItemViewHolder, position: Int) {
        val data = presenter.getItemAtPosition(position)
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return presenter.getItemCount()
    }

    override fun updateData() {
        notifyDataSetChanged()
    }
}

class WeatherItemViewHolder(private val binding: RecyclerWeatherItemBinding) :
    ViewHolder,
    RecyclerView.ViewHolder(binding.root) {

    override fun bind(item: FormattedHourData) {
        Utils.loadUrlImage(itemView.context, binding.iconImage, item.weather[0].icon)
        with(binding) {
            dateText.text = item.dtTxt
            temperatureText.text = itemView.context?.getString(
                R.string.temperature_value, item.main.temp
            )
            descriptionText.text = item.weather[0].description
        }
    }
}

interface IView {
    fun updateData()
}

interface ViewHolder {
    fun bind(item: FormattedHourData)
}