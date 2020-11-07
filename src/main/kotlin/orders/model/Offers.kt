package orders.model

open class BaseEntity {
    open val name: String = ""
    open val description: String = ""
    open fun calcCost(quantity: Int, totalCost: Int): Int = totalCost
}

/*
    each fun calcCost uses logic that calculates total cost of every item with special offer
 */
data class Apple(
        override val name: String = "apple",
        override val description: String = "buy one get one free"
) : BaseEntity() {
    override fun calcCost(quantity: Int, totalCost: Int) = if (quantity > 1) totalCost - (quantity / 2) * (totalCost / quantity) else totalCost
}

data class Orange(
        override val name: String = "orange",
        override val description: String = "3 for the price of 2"
) : BaseEntity() {
    override fun calcCost(quantity: Int, totalCost: Int) = if (quantity > 2) totalCost - (quantity / 3) * (totalCost / quantity) else totalCost
}