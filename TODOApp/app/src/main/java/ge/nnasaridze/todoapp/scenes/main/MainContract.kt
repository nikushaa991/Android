package ge.nnasaridze.todoapp.scenes.main

interface MainView {
    fun hidePinned()
    fun showPinned()
    fun resetFilter()
    fun refreshUI()

    fun gotoEditActivity(id: Int)
}

interface MainPresenter {
    fun onFilterChanged(text: CharSequence?)
    fun onFabPressed()

    fun getPinnedItemCount(): Int
    fun getPinnedItemAtPosition(position: Int): MainTodoEntity
    fun onPinnedItemPressed(position: Int)

    fun getOtherItemCount(): Int
    fun getOtherItemAtPosition(position: Int): MainTodoEntity
    fun onOtherItemPressed(position: Int)
}

interface MainViewHolder {
    fun bind(data: MainTodoEntity)
}

interface MainRecyclerAdapter {
    fun update()
}