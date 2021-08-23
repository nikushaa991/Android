package ge.nnasaridze.todoapp.scenes.main.recyclers

import androidx.recyclerview.widget.RecyclerView
import ge.nnasaridze.todoapp.databinding.ActivityMainRecyclerViewItemBinding
import ge.nnasaridze.todoapp.scenes.main.MainTodoEntity
import ge.nnasaridze.todoapp.scenes.main.MainViewHolder

class MainRecyclerViewHolder(private val binding: ActivityMainRecyclerViewItemBinding) :
    MainViewHolder,
    RecyclerView.ViewHolder(binding.root) {

    override fun bind(data: MainTodoEntity) {
        with(binding) {
            nameText.text = data.name
            checkbox1.text = data.text1
            checkbox2.text = data.text2
            checkbox3.text = data.text3

            checkbox1.visibility = data.visibility1
            checkbox2.visibility = data.visibility2
            checkbox3.visibility = data.visibility3

            ellipses.visibility = data.ellipsesVisibility

            pinnedNumber.text = data.pinnedCount.toString()
            pinnedLayoutItem.visibility = data.pinnedVisibility
        }
    }
}

