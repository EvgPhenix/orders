package orders.service

import orders.client.MailClient
import orders.model.Apple
import orders.model.CalculationResponse
import orders.model.Orange
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam
import java.net.ConnectException

@Service
class OrdersService(private val mailClient: MailClient) {

    fun getOffers() = getOffersMap().map { it.key + ": " + it.value.description }

    fun getCalc(calculationRequest: List<String>) =
            CalculationResponse("$" + getCalculations(calculationRequest) / 100.0)

    fun placeOrder(userId: String, mailAddress: String, calculationRequest: List<String>): String {
        val goodsAggregate = getGoodsDefinition(calculationRequest).filter { mapOffers.containsKey(it.key) } // count valid goods in format {orange=3, apple=2}
        val outOfStock = isOutOfStock(goodsAggregate)
        val totalCost = getCalc(calculationRequest).totalCost
        val orderDetails = goodsAggregate.toString()
        var result = ""
        // These things below actually depend on business decisions
        if (!outOfStock) {
            result = sendMailHandler(userId, mailAddress, true, orderDetails, totalCost)
            return if (result.equals("Email sent successfully")) {
                        String.format("Dear %s! You placed order that contains [%s] and costs %s. We sent you details to %s",
                                userId, orderDetails, totalCost, mailAddress)
                    } else {
                        String.format("Dear %s! You placed order that contains [%s] and costs %s. Thank you!",
                                userId, orderDetails, totalCost)
                    }
        } else {
            sendMailHandler(userId, mailAddress, false, orderDetails, totalCost)
            return "These goods are out of stock. Please place another order."
        }

    }
    // this code handle ConnectException if mail sender service isn't available
    private fun sendMailHandler(userId: String, mailAddress: String, isSuccess: Boolean, orderDetails: String, totalCost: String): String {
        var result = ""
        try {
            result = mailClient.sendMessage(userId, mailAddress, isSuccess, orderDetails, totalCost)
        } catch (e: ConnectException) {
           e.printStackTrace()
        } finally {
            return result
        }
    }

    private fun isOutOfStock(goods: Map<String, Int>): Boolean =
            goods.map { manageStocks(it) }.contains(true)

    private fun manageStocks(entry: Map.Entry<String, Int>): Boolean {
        if (mapQuantity[entry.key]?.minus(entry.value)!! > 0) {
            mapQuantity[entry.key]?.minus(entry.value)?.let { mapQuantity.put(entry.key, it) }
            return false
        }
        else {
            return true
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
        // stocks are here
        val mapQuantity = mutableMapOf("apple" to 10, "orange" to 11)
    }

}