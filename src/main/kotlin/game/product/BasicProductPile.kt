package game.product

data class BasicProductPile(
    override val maximum: Long,
    override var productCount: Int,
    override val type: ProductType,
    override var price: Int
) : ProductPile {
}