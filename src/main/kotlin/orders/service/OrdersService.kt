package com.american.express.orders.service

import com.american.express.orders.model.CalculationResponse
import orders.model.Apple
import orders.model.Orange
import org.springframework.stereotype.Service

@Service
class OrdersService {

    fun getOffers() = getOffersMap().map { it.key + ": " + it.value.description }

    fun getCalc(calculationRequest: List<String>) =
           CalculationResponse( "$" + getCalculations(calculationRequest) / 100.0)

    fun getCalculations(calculationRequest: List<String>): Int {
        if (calculationRequest.size == 0) return 0
        return calculationRequest
                .groupingBy { it }
                .eachCount()
                .map { countCost(it) }
                .map { it.second?: error(0).toInt() }
                .reduce { sum, element -> sum + element }
    }

    fun countCost(entry: Map.Entry<String, Int>): Pair<String, Int?> {
        if (getOffersMap().containsKey(entry.key)) {
            val totalCost = mapOffers[entry.key]?.times(entry.value)
            return Pair(entry.key, totalCost?.let { getOffersMap()[entry.key]?.calcCost(entry.value, it) })
        } else return Pair(entry.key, 0)
    }

    fun getOffersMap() = mapOf("apple" to apple, "orange" to orange)

    companion object {
        val mapOffers = mapOf("apple" to 60, "orange" to 25)
        val apple = Apple()
        val orange = Orange()
    }

}
