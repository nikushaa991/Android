package ge.nnasaridze.alarmapp.scenes.main.recycler

import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import ge.nnasaridze.alarmapp.databinding.RecyclerViewItemBinding
import ge.nnasaridze.alarmapp.scenes.main.repository.MainEntity
import ge.nnasaridze.alarmapp.scenes.main.MainViewHolder
import ge.nnasaridze.alarmapp.scenes.main.getFormattedTime

class MainRecyclerViewHolder(
    private val binding: RecyclerViewItemBinding,
    private val switchHandler: (position: Int) -> Unit
) :
    MainViewHolder,
    RecyclerView.ViewHolder(binding.root) {

    override fun bind(data: MainEntity) {
        with(binding) {
            timeText.text = getFormattedTime(data.hour, data.minute)
            timeSwitch.setOnCheckedChangeListener(null)
            timeSwitch.isChecked = data.isActive
            timeSwitch.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
                switchHandler(adapterPosition)
            }
        }
    }
}
