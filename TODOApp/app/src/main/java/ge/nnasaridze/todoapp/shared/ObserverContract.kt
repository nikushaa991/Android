package ge.nnasaridze.todoapp.shared

interface Observer {
    fun dataChanged()
}

interface Observable {
    fun register(observer: Observer)
    fun unregister(observer: Observer)
}