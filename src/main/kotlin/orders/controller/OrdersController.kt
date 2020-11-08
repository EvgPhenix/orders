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

    @PostMapping(value = [ORDERS])
    fun placeOrder(@RequestHeader(USER_ID) userId: String,
                   @RequestParam("mailAddress", defaultValue = true.toString()) mailAddress: String,
                   @RequestBody calculationRequest: List<String>
    ): String =
            ordersService.placeOrder(userId, mailAddress, calculationRequest)

    companion object {
        const val OFFERS = "/offers"
        const val CALCULATIONS = "/calculations"
        const val ORDERS = "/orders"
        const val USER_ID = "USER_ID"
    }
}
