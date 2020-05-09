package game.country

class BasicCountry(override val name: String) : Country {
    override fun toString(): String {
        return "BasicCountry(name='$name')\n"
    }
}