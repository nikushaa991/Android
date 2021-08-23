package ge.nnasaridze.todoapp.scenes.edit

interface EditView {
    fun setPinnedOn()
    fun setPinnedOff()
    fun setName(name: String)
    fun focusName()
    fun refreshUI()

    fun showChecked()
    fun hideChecked()

    fun gotoMainActivity()
}

interface EditPresenter {
    fun onBackPressed()
    fun onPinPressed()
    fun onAddPressed(item: String)
    fun onNameChanged(s: CharSequence?)

    fun onItemTextUpdated(position: Int, text: String)
    fun onRemovePressed(position: Int)
    fun getUncheckedItemCount(): Int
    fun getUncheckedItemAtPosition(position: Int): EditTodoEntity
    fun onUncheckedItemPressed(position: Int)

    fun getCheckedItemCount(): Int
    fun getCheckedItemAtPosition(position: Int): EditTodoEntity
    fun onCheckedItemPressed(position: Int)

}

interface EditViewHolder {
    fun bind(data: EditTodoEntity)
}

interface EditRecyclerAdapter {
    fun update()
}