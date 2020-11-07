package orders

import com.american.express.orders.OrdersApplication
import com.american.express.orders.controller.OrdersController
import com.american.express.orders.controller.OrdersController.Companion.CALCULATIONS
import com.american.express.orders.controller.OrdersController.Companion.USER_ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.charset.Charset

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = [ OrdersApplication::class]
)
@AutoConfigureMockMvc
class OrdersControllerTest(@Autowired val mockMvc: MockMvc ) {

	@Test
	fun contextLoads() {
		assertThat(OrdersController).isNotNull();
	}

	@Test
	fun `Must return normal calculations`() {

		val json = "[\"orange\", \"apple\", \"apple\", \"apple\"]"
		val response = mockMvc
				.perform(get(CALCULATIONS).header(USER_ID, USER_ID).contentType(MediaType.APPLICATION_JSON)
						.content(json))
		response
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn()
		println(response.andReturn().response.getContentAsString(Charset.defaultCharset()))
		assertEquals(response.andReturn().response.getContentAsString(Charset.defaultCharset()), "{\"totalCost\":\"\$2.05\"}")
	}
}
