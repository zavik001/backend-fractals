package backend.academy.fractals.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Configuration
@EnableJpaRepositories(basePackages = "backend.academy.fractals.repository.jpa",
                        includeFilters = @ComponentScan.Filter(Repository.class))
public class JpaConfig {
}
