package game.product

class BasicProduct(private var productType: ProductType, private var price : Int) : Product {

    override fun getType(): ProductType {
        return productType
    }

    override fun getPrice(): Int {
        return price
    }
}