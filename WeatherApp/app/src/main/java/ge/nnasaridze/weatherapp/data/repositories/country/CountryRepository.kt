package ge.nnasaridze.weatherapp.data.repositories.country

interface CountryRepository {
    fun getCurrentCountryCapital(): String
    fun setCurrentCountry(country: Country)
    fun subscribe(subscriber: Observer)
}

object CountryRepositoryImpl : CountryRepository {

    private var currentCountry = Country.GEORGIA
    private val observers = arrayListOf<Observer>()

    override fun getCurrentCountryCapital(): String = currentCountry.capitalName

    override fun setCurrentCountry(country: Country) {
        currentCountry = country
        observers.forEach { it.observableChanged() }
    }

    override fun subscribe(subscriber: Observer) {
        observers.add(subscriber)
        subscriber.observableChanged()
    }
}

enum class Country(val capitalName: String) {
    GEORGIA("TBILISI"),
    UK("LONDON"),
    JAMAICA("KINGSTON")
}

interface Observer {
    fun observableChanged()
}


