package com.american.express.orders.service

import com.american.express.orders.model.CalculationResponse
import org.springframework.stereotype.Service

@Service
class OrdersService {

    fun getCalc(calculationRequest: List<String>) =
           CalculationResponse( "$" + getCalculations(calculationRequest) / 100.0)

    fun getCalculations(calculationRequest: List<String>): Int {
        if (calculationRequest.size == 0) return 0
        return calculationRequest
                .map { (mapOffers[it.toLowerCase()] ?: error(0)).toInt() }
                .reduce { sum, element -> sum + element }
    }

    companion object {
        val mapOffers = mapOf("apple" to 60, "orange" to 25)
    }
}
