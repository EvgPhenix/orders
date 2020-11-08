package orders

import orders.controller.OrdersController
import orders.controller.OrdersController.Companion.CALCULATIONS
import orders.controller.OrdersController.Companion.USER_ID
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
class OrdersControllerTest(@Autowired val mockMvc: MockMvc) {

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
		assertEquals(response.andReturn().response.getContentAsString(Charset.defaultCharset()), "{\"totalCost\":\"\$1.45\"}")
	}

	@Test
	fun `Must return normal calculations with offers`() {

		val json = "[\"orange\", \"apple\", \"apple\", \"orange\", \"orange\"]"
		val response = mockMvc
				.perform(get(CALCULATIONS).header(USER_ID, USER_ID).contentType(MediaType.APPLICATION_JSON)
						.content(json))
		response
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn()
		println(response.andReturn().response.getContentAsString(Charset.defaultCharset()))
		assertEquals(response.andReturn().response.getContentAsString(Charset.defaultCharset()), "{\"totalCost\":\"\$1.1\"}")
	}

	@Test
	fun `Must return normal calculations with offers with elements that are not present`() {

		val json = "[\"orange\", \"apple\", \"apple\", \"orange\", \"orange\", \"cucumber\"]"
		val response = mockMvc
				.perform(get(CALCULATIONS).header(USER_ID, USER_ID).contentType(MediaType.APPLICATION_JSON)
						.content(json))
		response
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn()
		println(response.andReturn().response.getContentAsString(Charset.defaultCharset()))
		assertEquals(response.andReturn().response.getContentAsString(Charset.defaultCharset()), "{\"totalCost\":\"\$1.1\"}")
	}

	@Test
	fun `Must return normal calculations with empty body`() {

		val json = "[]"
		val response = mockMvc
				.perform(get(CALCULATIONS).header(USER_ID, USER_ID).contentType(MediaType.APPLICATION_JSON)
						.content(json))
		response
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn()
		println(response.andReturn().response.getContentAsString(Charset.defaultCharset()))
		assertEquals(response.andReturn().response.getContentAsString(Charset.defaultCharset()), "{\"totalCost\":\"\$0.0\"}")
	}

	@Test
	fun `Must throw exception without user_id`() {

		val json = "[]"
		val response = mockMvc
				.perform(get(CALCULATIONS).contentType(MediaType.APPLICATION_JSON)
						.content(json))
		response
				.andExpect(status().is4xxClientError)
				.andDo(print())
				.andReturn()
	}

	@Test
	fun `Must throw exception with no body`() {

		val response = mockMvc
				.perform(get(CALCULATIONS).header(USER_ID, USER_ID))
		response
				.andExpect(status().is4xxClientError)
				.andDo(print())
				.andReturn()
	}
}
