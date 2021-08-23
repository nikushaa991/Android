package ge.nnasaridze.todoapp.scenes.main.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.nnasaridze.todoapp.databinding.ActivityMainRecyclerViewItemBinding
import ge.nnasaridze.todoapp.scenes.main.MainPresenter
import ge.nnasaridze.todoapp.scenes.main.MainRecyclerAdapter

class MainOtherRecyclerAdapter(private val presenter: MainPresenter) :
    MainRecyclerAdapter,
    RecyclerView.Adapter<MainRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val vh = MainRecyclerViewHolder(
            ActivityMainRecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        vh.itemView.setOnClickListener { presenter.onOtherItemPressed(vh.adapterPosition) }
        return vh
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        val data = presenter.getOtherItemAtPosition(position)
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return presenter.getOtherItemCount()
    }

    override fun update() {
        notifyDataSetChanged()
    }
}

