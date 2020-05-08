package game.product

interface ProductPile : Product {
    val maximum : Long
    var productCount : Int
}