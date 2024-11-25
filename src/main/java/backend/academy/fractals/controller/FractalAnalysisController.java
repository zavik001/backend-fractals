package backend.academy.fractals.controller;

import backend.academy.fractals.service.FractalRedisService;
import backend.academy.fractals.service.model.GraphPoint;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class FractalAnalysisController {

    private final FractalRedisService redisService;

    @GetMapping("/single")
    public ResponseEntity<List<GraphPoint>> getSingleThreadedData() {
        log.info("Received request to fetch single-threaded graph data.");
        List<GraphPoint> singleThreaded = redisService.getPointsByType("single-threaded");
        log.info("Fetched single-threaded points: {}", singleThreaded);
        return ResponseEntity.ok(singleThreaded);
    }

    @GetMapping("/multi")
    public ResponseEntity<List<GraphPoint>> getMultiThreadedData() {
        log.info("Received request to fetch multi-threaded graph data.");
        List<GraphPoint> multiThreaded = redisService.getPointsByType("multi-threaded");
        log.info("Fetched multi-threaded points: {}", multiThreaded);
        return ResponseEntity.ok(multiThreaded);
    }
}
