package backend.academy.fractals.config;

import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
public class AsyncConfig {

    private static final int MAX_POOL_MULTIPLIER = 2;
    private static final int QUEUE_CAPACITY_MULTIPLIER = 10;

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(availableProcessors);
        executor.setMaxPoolSize(availableProcessors * MAX_POOL_MULTIPLIER);
        executor.setQueueCapacity(availableProcessors * QUEUE_CAPACITY_MULTIPLIER);
        executor.setThreadNamePrefix("Fractal-Generator-");
        executor.initialize();

        log.info("TaskExecutor initialized with core pool size: {}, max pool size: {}, queue capacity: {}",
                executor.getCorePoolSize(), executor.getMaxPoolSize(), executor.getQueueCapacity());

        return executor;
    }
}
