package backend.academy.fractals.scheduler;

import backend.academy.fractals.service.FractalRedisService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class RediseCleaner {

    private final FractalRedisService redisService;

    @Scheduled(cron = "0 0 0 */3 * *") // Every 3 days
    public void clearOldData() {
        log.info("Startung to clean redis data...");

        redisService.clearData();

        log.info("Successfully cleaned redis data.");
    }
}
