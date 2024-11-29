package backend.academy.fractals.scheduler;

import backend.academy.fractals.repository.jpa.FractalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntityCleaner {

    private final FractalRepository fractalRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Every day at 00:00
    public void clearFractalData() {
        log.info("Starting to clear the fractal_entity table...");

        fractalRepository.deleteAll();

        log.info("Successfully cleared the fractal_entity table.");
    }
}
