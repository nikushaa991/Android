package ge.nnasaridze.todoapp.scenes.edit.recyclers

import androidx.recyclerview.widget.RecyclerView
import ge.nnasaridze.todoapp.databinding.ActivityEditRecyclerViewCheckedItemBinding
import ge.nnasaridze.todoapp.scenes.edit.EditTodoEntity
import ge.nnasaridze.todoapp.scenes.edit.EditViewHolder

class EditCheckedRecyclerViewHolder(
    private val binding: ActivityEditRecyclerViewCheckedItemBinding,
    handler: (position: Int) -> Unit
) :
    EditViewHolder,
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.checkedCheckbox.setOnCheckedChangeListener { _, check ->
            binding.checkedCheckbox.isChecked = !check
            handler(adapterPosition)
        }
    }

    override fun bind(data: EditTodoEntity) {
        binding.checkedCheckbox.text = data.name
    }
}
