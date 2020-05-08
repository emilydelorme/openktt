package game.product

data class BasicProduct(override val type: ProductType, override var price: Int) : Product