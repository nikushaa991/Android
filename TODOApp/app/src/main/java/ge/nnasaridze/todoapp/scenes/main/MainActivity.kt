package ge.nnasaridze.todoapp.scenes.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ge.nnasaridze.todoapp.databinding.ActivityMainBinding
import ge.nnasaridze.todoapp.scenes.edit.EditActivity
import ge.nnasaridze.todoapp.scenes.main.recyclers.MainOtherRecyclerAdapter
import ge.nnasaridze.todoapp.scenes.main.recyclers.MainPinnedRecyclerAdapter

class MainActivity : MainView, AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter
    private var pinnedRecycler: MainRecyclerAdapter? = null
    private var otherRecycler: MainRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        presenter = MainPresenterImpl(this, this)

        with(binding) {
            searchText.doAfterTextChanged { onFilterChanged(it.toString()) }
            fab.setOnClickListener { onFabClicked() }

            mainPinnedRecycler.adapter = MainPinnedRecyclerAdapter(presenter)
            mainPinnedRecycler.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            mainOtherRecycler.adapter = MainOtherRecyclerAdapter(presenter)
            mainOtherRecycler.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            pinnedRecycler = mainPinnedRecycler.adapter as MainRecyclerAdapter
            otherRecycler = mainOtherRecycler.adapter as MainRecyclerAdapter
        }

        supportActionBar?.hide()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        resetFilter()
    }


    override fun hidePinned() {
        binding.pinnedLayout.visibility = View.GONE
    }

    override fun showPinned() {
        binding.pinnedLayout.visibility = View.VISIBLE
    }

    override fun resetFilter() {
        binding.searchText.setText("")
    }

    override fun refreshUI() {
        pinnedRecycler?.update()
        otherRecycler?.update()
    }

    override fun gotoEditActivity(id: Int) {
        startActivity(Intent(this, EditActivity::class.java).apply {
            putExtra("id", id)
        })
    }

    private fun onFabClicked() {
        presenter.onFabPressed()
    }

    private fun onFilterChanged(s: CharSequence?) {
        presenter.onFilterChanged(s)
    }
}