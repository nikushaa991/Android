package ge.nnasaridze.todoapp.scenes.edit.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.nnasaridze.todoapp.databinding.ActivityEditRecyclerViewCheckedItemBinding
import ge.nnasaridze.todoapp.scenes.edit.EditPresenter
import ge.nnasaridze.todoapp.scenes.edit.EditRecyclerAdapter

class EditCheckedRecyclerAdapter(private val presenter: EditPresenter) : EditRecyclerAdapter,
    RecyclerView.Adapter<EditCheckedRecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditCheckedRecyclerViewHolder {
        return EditCheckedRecyclerViewHolder(
            ActivityEditRecyclerViewCheckedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        , presenter::onCheckedItemPressed)
    }

    override fun onBindViewHolder(holder: EditCheckedRecyclerViewHolder, position: Int) {
        val data = presenter.getCheckedItemAtPosition(position)
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return presenter.getCheckedItemCount()
    }

    override fun update() {
        notifyDataSetChanged()
    }


}
