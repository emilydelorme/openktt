package game.product

data class BasicProductPile(
    override val maximum: Int,
    override var productCount: Int,
    override val type: ProductType
) : ProductPile