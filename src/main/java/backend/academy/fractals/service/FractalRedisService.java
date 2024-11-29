package backend.academy.fractals.service;

import backend.academy.fractals.entity.GraphPointEntity;
import backend.academy.fractals.repository.redis.GraphPointRepository;
import backend.academy.fractals.service.model.redis.GraphPoint;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FractalRedisService {

    private final GraphPointRepository graphPointRepository;

    public void addPoint(String generatorType, GraphPoint point) {
        log.info("Start adding point for generator type [{}]: {}", generatorType, point);

        GraphPointEntity entity = new GraphPointEntity(
                java.util.UUID.randomUUID().toString(),
                generatorType,
                point.iterations(),
                point.timeTaken());

        log.info("Constructed GraphPointEntity: {}", entity);

        graphPointRepository.save(entity);

        // List<GraphPointEntity> savedPoints = graphPointRepository.findByGeneratorType(generatorType);
        // log.info("Current points in repository for generator type [{}]: {}", // generatorType, savedPoints);
    }

    @Async("taskExecutor")
    public CompletableFuture<List<GraphPoint>> getPointsByType(String generatorType) {
        log.info("Fetching points for generator type: {}", generatorType);

        List<GraphPoint> points = graphPointRepository.findByGeneratorType(generatorType)
                .stream()
                .map(entity -> new GraphPoint(entity.iterations(), entity.timeTaken()))
                .collect(Collectors.toList());

        log.info("Points fetched for generator type [{}]: {}", generatorType, points);
        return CompletableFuture.completedFuture(points);
    }

    public void clearData() {
        // log.warn("Clearing all data from the repository.");
        graphPointRepository.deleteAll();
        // log.info("All data cleared.");
    }
}
