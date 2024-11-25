package backend.academy.fractals.service;

import backend.academy.fractals.entity.GraphPointEntity;
import backend.academy.fractals.repository.redis.GraphPointRepository;
import backend.academy.fractals.service.model.GraphPoint;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        try {
            graphPointRepository.save(entity);
            log.info("Point successfully saved in repository: {}", entity);
        } catch (Exception e) {
            log.error("Failed to save point for generator type [{}]: {}", generatorType, e.getMessage(), e);
            throw e;
        }

        // List<GraphPointEntity> savedPoints = graphPointRepository.findByGeneratorType(generatorType);
        // log.info("Current points in repository for generator type [{}]: {}", generatorType, savedPoints);
    }

    public List<GraphPoint> getPointsByType(String generatorType) {
        log.info("Fetching points for generator type: {}", generatorType);

        List<GraphPoint> points = graphPointRepository.findByGeneratorType(generatorType)
                .stream()
                .map(entity -> new GraphPoint(entity.iterations(), entity.timeTaken()))
                .collect(Collectors.toList());

        // log.info("Points fetched for generator type [{}]: {}", generatorType, points);
        return points;
    }

    public void clearData() {
        // log.warn("Clearing all data from the repository.");
        graphPointRepository.deleteAll();
        // log.info("All data cleared.");
    }
}
