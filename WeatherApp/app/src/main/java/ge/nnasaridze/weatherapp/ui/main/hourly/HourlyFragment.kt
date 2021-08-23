package ge.nnasaridze.weatherapp.ui.main.hourly

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ge.nnasaridze.weatherapp.R
import ge.nnasaridze.weatherapp.databinding.FragmentHourlyBinding

interface HourlyView

class HourlyFragment : HourlyView, Fragment() {

    private lateinit var binding: FragmentHourlyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHourlyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(
            ColorDrawable(
                ContextCompat.getColor(requireContext(), R.color.white)
            )
        )

        with(binding.hourlyRecycler) {
            adapter = MainHourlyRecyclerAdapter(HourlyPresenterImpl())
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(itemDecoration)
        }
    }
}