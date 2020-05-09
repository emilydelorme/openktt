package game.product

interface ProductPile : Product {
    val maximum: Int
    var productCount: Int
}