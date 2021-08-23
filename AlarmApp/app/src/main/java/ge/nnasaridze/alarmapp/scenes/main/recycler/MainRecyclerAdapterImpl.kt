package ge.nnasaridze.alarmapp.scenes.main.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.nnasaridze.alarmapp.databinding.RecyclerViewItemBinding
import ge.nnasaridze.alarmapp.scenes.main.MainPresenter
import ge.nnasaridze.alarmapp.scenes.main.MainRecyclerAdapter

class MainRecyclerAdapterImpl(private val presenter: MainPresenter) :
    MainRecyclerAdapter,
    RecyclerView.Adapter<MainRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val vh = MainRecyclerViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            presenter::onSwitchPressed
        )
        vh.itemView.setOnLongClickListener {
            presenter.onAlarmLongPressed(vh.adapterPosition)
            true
        }
        return vh
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        val data = presenter.getItemAt(position)
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return presenter.getItemCount()
    }

    override fun update() {
        notifyDataSetChanged()
    }
}