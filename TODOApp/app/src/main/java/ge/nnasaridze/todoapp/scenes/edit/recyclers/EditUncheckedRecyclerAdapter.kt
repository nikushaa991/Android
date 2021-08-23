package ge.nnasaridze.todoapp.scenes.edit.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.nnasaridze.todoapp.databinding.ActivityEditRecyclerViewUncheckedItemBinding
import ge.nnasaridze.todoapp.scenes.edit.EditPresenter
import ge.nnasaridze.todoapp.scenes.edit.EditRecyclerAdapter

class EditUncheckedRecyclerAdapter(private val presenter: EditPresenter) : EditRecyclerAdapter,
    RecyclerView.Adapter<EditUncheckedRecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditUncheckedRecyclerViewHolder {
        return EditUncheckedRecyclerViewHolder(
            ActivityEditRecyclerViewUncheckedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            presenter::onUncheckedItemPressed,
            presenter::onRemovePressed,
            presenter::onItemTextUpdated
        )
    }

    override fun onBindViewHolder(holder: EditUncheckedRecyclerViewHolder, position: Int) {
        val data = presenter.getUncheckedItemAtPosition(position)
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return presenter.getUncheckedItemCount()
    }

    override fun update() {
        notifyDataSetChanged()
    }


}
