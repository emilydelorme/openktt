package game.building

interface Building {
    fun getName() : String
    fun getCost() : Double
    fun tick()
}