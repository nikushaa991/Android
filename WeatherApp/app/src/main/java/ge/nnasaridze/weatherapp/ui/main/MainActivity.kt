package ge.nnasaridze.weatherapp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ge.nnasaridze.weatherapp.R
import ge.nnasaridze.weatherapp.data.repositories.country.Country
import ge.nnasaridze.weatherapp.databinding.ActivityMainBinding
import ge.nnasaridze.weatherapp.ui.main.hourly.HourlyFragment
import ge.nnasaridze.weatherapp.ui.main.today.MainTodayFragment

interface MainView {
    fun setCapitalName(name: String)
    fun setDayBackground()
    fun setNightBackground()
    fun setTodayFragment()
    fun setHourlyFragment()
    fun showError(message: String)
}

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter
    private lateinit var pager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        pager = binding.fragmentsPager
        pager.adapter = MainFragmentsPagerAdapter(
            this,
            arrayListOf(MainTodayFragment(), HourlyFragment())
        )

        with(binding) {
            buttonGeorgia.setOnClickListener { onCountryTap(Country.GEORGIA) }
            buttonUK.setOnClickListener { onCountryTap(Country.UK) }
            buttonJamaica.setOnClickListener { onCountryTap(Country.JAMAICA) }
            buttonToday.setOnClickListener { onTodayTap() }
            buttonHourly.setOnClickListener { onHourlyTap() }
        }

        supportActionBar?.hide()
        setContentView(binding.root)
        presenter = MainPresenterImpl(this)
    }

    private fun setBackgroundColor(colorId: Int) {
        binding.mainLayout.setBackgroundColor(ContextCompat.getColor(this, colorId))
    }

    private fun onCountryTap(country: Country) {
        presenter.onCountryTapped(country)
    }

    private fun onTodayTap() {
        presenter.onTodayTapped()
    }

    private fun onHourlyTap() {
        presenter.onHourlyTapped()
    }

    override fun setCapitalName(name: String) {
        binding.textCapital.text = name
    }

    override fun setDayBackground() {
        setBackgroundColor(R.color.bg_day)
    }

    override fun setNightBackground() {
        setBackgroundColor(R.color.bg_night)
    }

    override fun setTodayFragment() {
        pager.currentItem = FRAGMENT_TODAY_INDEX
    }

    override fun setHourlyFragment() {
        pager.currentItem = FRAGMENT_HOURLY_INDEX
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    class MainFragmentsPagerAdapter(
        activity: FragmentActivity,
        private val fragments: ArrayList<Fragment>
    ) : FragmentStateAdapter(activity) {

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int) = fragments[position]
    }

    companion object {
        const val FRAGMENT_TODAY_INDEX = 0
        const val FRAGMENT_HOURLY_INDEX = 1
    }
}

