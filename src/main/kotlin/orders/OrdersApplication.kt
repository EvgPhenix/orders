package orders

import orders.config.CustomConfigs
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.kafka.annotation.EnableKafka

@EnableFeignClients
@EnableKafka
@EnableConfigurationProperties(CustomConfigs::class)
@SpringBootApplication
class OrdersApplication

fun main(args: Array<String>) {
	runApplication<OrdersApplication>(*args)
}
