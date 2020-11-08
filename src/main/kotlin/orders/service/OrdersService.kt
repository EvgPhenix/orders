package orders.service

import orders.client.MailClient
import orders.model.Apple
import orders.model.CalculationResponse
import orders.model.Orange
import org.springframework.stereotype.Service

@Service
class OrdersService(private val mailClient: MailClient) {

    fun getOffers() = getOffersMap().map { it.key + ": " + it.value.description }

    fun getCalc(calculationRequest: List<String>) =
            CalculationResponse("$" + getCalculations(calculationRequest) / 100.0)

    fun placeOrder(userId: String, mailAddress: String, calculationRequest: List<String>): String {
        val outOfStock = false
        val totalCost = getCalc(calculationRequest).totalCost
        val orderDetails = getGoodsDefinition(calculationRequest).filter { mapOffers.containsKey(it.key) }.toString()
        var result = ""
        if (!outOfStock) {
            result = mailClient.sendMessage(userId, mailAddress, true, orderDetails, totalCost)
        } else {
            return "These goods are out of stock. Please place another order."
        }
        if (result.equals("Email sent successfully")) {
            return String.format("Dear %s! You placed order that contains [%s] and costs %s. We sent you details to %s",
            userId, orderDetails, totalCost, mailAddress)
        } else {
            return String.format("Dear %s! You placed order that contains [%s] and costs %s. Thank you!",
                    userId, orderDetails, totalCost)
        }
    }

    private fun getCalculations(calculationRequest: List<String>): Int {
        if (calculationRequest.isEmpty()) return 0
        return getGoodsDefinition(calculationRequest)
                .map { countCost(it) }
                .map { it.second ?: error(0) }
                .sum()
    }

    private fun getGoodsDefinition(calculationRequest: List<String>): Map<String, Int> {
        return calculationRequest
                .groupingBy { it }
                .eachCount()
    }

    private fun countCost(entry: Map.Entry<String, Int>): Pair<String, Int?> {
        if (getOffersMap().containsKey(entry.key)) {
            val totalCost = mapOffers[entry.key]?.times(entry.value)
            return Pair(entry.key, totalCost?.let { getOffersMap()[entry.key]?.calcCost(entry.value, it) })
        } else return Pair(entry.key, 0)
    }

    private fun getOffersMap() = mapOf("apple" to apple, "orange" to orange)

    companion object {
        val mapOffers = mapOf("apple" to 60, "orange" to 25)
        val apple = Apple()
        val orange = Orange()
    }

}
