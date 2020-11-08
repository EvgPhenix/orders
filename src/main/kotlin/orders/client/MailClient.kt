package orders.client

import orders.controller.OrdersController.Companion.USER_ID
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "mail", url = "\${mail.url}")
interface MailClient {
    @PostMapping("/mails")
    fun sendMessage(@RequestHeader(USER_ID) userId: String,
                               @RequestParam("mailAddress", defaultValue = true.toString()) mailAddress: String,
                               @RequestParam("isSuccess", defaultValue = true.toString()) isSuccess: Boolean,
                               @RequestParam("orderDetails", defaultValue = "") orderDetails: String,
                               @RequestParam("totalCost", defaultValue = "") totalCost: String
    ): String
}