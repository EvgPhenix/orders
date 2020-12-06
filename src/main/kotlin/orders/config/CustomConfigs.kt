package orders.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "custom-configs")
class CustomConfigs {
  var autoStart: Boolean = true;
}
