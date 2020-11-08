package orders.controller

import orders.model.CalculationResponse
import orders.service.OrdersService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping(produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
@RestController
class OrdersController(private val ordersService: OrdersService) {

    @GetMapping(value = [OFFERS])
    fun getOffers(@RequestHeader(USER_ID) userId: String
                 ): List<String> = ordersService.getOffers()

    @GetMapping(value = [CALCULATIONS])
    fun getCalculations(@RequestHeader(USER_ID) userId: String,
                        @RequestBody calculationRequest: List<String>
                        ): CalculationResponse =
            ordersService.getCalc(calculationRequest)

    companion object {
        const val OFFERS = "/offers"
        const val CALCULATIONS = "/calculations"
        const val USER_ID = "USER_ID"
    }
}
