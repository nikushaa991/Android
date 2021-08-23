package ge.nnasaridze.todoapp.scenes.edit

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import ge.nnasaridze.todoapp.R
import ge.nnasaridze.todoapp.databinding.ActivityEditBinding
import ge.nnasaridze.todoapp.scenes.edit.recyclers.EditCheckedRecyclerAdapter
import ge.nnasaridze.todoapp.scenes.edit.recyclers.EditUncheckedRecyclerAdapter
import ge.nnasaridze.todoapp.shared.Constants.NEW

class EditActivity : AppCompatActivity(), EditView {
    private lateinit var binding: ActivityEditBinding
    private lateinit var presenter: EditPresenter
    private var uncheckedRecycler: EditRecyclerAdapter? = null
    private var checkedRecycler: EditRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                presenter.onBackPressed()
            }
        })

        binding = ActivityEditBinding.inflate(layoutInflater)
        presenter = EditPresenterImpl(intent.getIntExtra("id", NEW), this, this)

        with(binding) {
            editUncheckedRecycler.adapter = EditUncheckedRecyclerAdapter(presenter)
            editUncheckedRecycler.layoutManager = LinearLayoutManager(this@EditActivity)
            editCheckedRecycler.adapter = EditCheckedRecyclerAdapter(presenter)
            editCheckedRecycler.layoutManager = LinearLayoutManager(this@EditActivity)

            backButton.setOnClickListener { presenter.onBackPressed() }
            pinButton.setOnClickListener { presenter.onPinPressed() }
            addButton.setOnClickListener { onAddPressed() }

            nameText.doAfterTextChanged {
                onNameChanged(it.toString())
            }

            checkedRecycler = editCheckedRecycler.adapter as EditRecyclerAdapter
            uncheckedRecycler = editUncheckedRecycler.adapter as EditRecyclerAdapter
        }

        supportActionBar?.hide()
        setContentView(binding.root)
    }

    override fun setPinnedOn() {
        binding.pinButton.setImageResource(R.drawable.ic_pinned)
    }

    override fun setPinnedOff() {
        binding.pinButton.setImageResource(R.drawable.ic_pin)
    }

    override fun setName(name: String) {
        binding.nameText.setText(name)
    }

    override fun focusName() {
        binding.nameText.requestFocus()
    }

    override fun showChecked() {
        binding.checkedLayout.visibility = View.VISIBLE
    }

    override fun hideChecked() {
        binding.checkedLayout.visibility = View.GONE
    }

    override fun gotoMainActivity() {
        finish()
    }

    override fun refreshUI() {
        checkedRecycler?.update()
        uncheckedRecycler?.update()
    }

    private fun onAddPressed() {
        presenter.onAddPressed(binding.itemNameText.text.toString())
    }

    private fun onNameChanged(s: CharSequence?) {
        presenter.onNameChanged(s)
    }
}