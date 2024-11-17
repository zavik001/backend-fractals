package backend.academy.fractals.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic fractalsTopic() {
        return new NewTopic("fractals", 1, (short) 1);
    }
}
