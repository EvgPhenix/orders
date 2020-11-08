package orders.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CalculationResponse (
        val totalCost: String
) : Serializable {
    companion object {
        const val serialVersionUID: Long = 1L
    }
}