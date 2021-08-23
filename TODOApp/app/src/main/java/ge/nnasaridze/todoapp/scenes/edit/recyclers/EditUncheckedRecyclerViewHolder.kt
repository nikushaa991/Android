package ge.nnasaridze.todoapp.scenes.edit.recyclers

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import ge.nnasaridze.todoapp.databinding.ActivityEditRecyclerViewUncheckedItemBinding
import ge.nnasaridze.todoapp.scenes.edit.EditTodoEntity
import ge.nnasaridze.todoapp.scenes.edit.EditViewHolder

class EditUncheckedRecyclerViewHolder(
    private val binding: ActivityEditRecyclerViewUncheckedItemBinding,
    checkHandler: (position: Int) -> Unit,
    removeHandler: (position: Int) -> Unit,
    updateHandler: (position: Int, text: String) -> Unit
) :
    EditViewHolder,
    RecyclerView.ViewHolder(binding.root) {
    init {
        with(binding) {
            uncheckedCheckbox.setOnCheckedChangeListener { _, check ->
                uncheckedCheckbox.isChecked = !check
                checkHandler(adapterPosition)
            }
            itemText.doAfterTextChanged {
                updateHandler(adapterPosition, it.toString())
            }
            deleteButton.setOnClickListener { removeHandler(adapterPosition) }
        }
    }

    override fun bind(data: EditTodoEntity) {
        with(binding) {
            with(itemText) {
                setText(data.name)
                isFocusable = data.focused
                isFocusableInTouchMode = data.focused
                if (text.isBlank())
                    requestFocus()
            }
            deleteButton.visibility = if (data.focused) View.VISIBLE else View.GONE

        }
    }
}
