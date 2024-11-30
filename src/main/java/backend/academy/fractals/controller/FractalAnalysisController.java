package backend.academy.fractals.controller;

import backend.academy.fractals.service.FractalRedisService;
import backend.academy.fractals.service.model.redis.GraphPoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class FractalAnalysisController {

    private final FractalRedisService redisService;

    @PostMapping("/data")
    public ResponseEntity<Map<String, List<GraphPoint>>> getAllGraphData(@RequestBody Map<String, Object> requestData) {
        log.info("Received POST request with data: {}", requestData);

        if (!requestData.containsKey("input") || !"1".equals(requestData.get("input").toString())) {
            log.warn("Invalid input data: {}", requestData);
            return ResponseEntity.badRequest().body(null);
        }

        CompletableFuture<List<GraphPoint>> singleThreadedFuture = redisService.getPointsByType("single-threaded");
        CompletableFuture<List<GraphPoint>> multiThreadedFuture = redisService.getPointsByType("multi-threaded");

        CompletableFuture.allOf(singleThreadedFuture, multiThreadedFuture).join();

        try {
            List<GraphPoint> singleThreaded = singleThreadedFuture.get();
            List<GraphPoint> multiThreaded = multiThreadedFuture.get();

            log.info("Fetched single-threaded points: {}", singleThreaded);
            log.info("Fetched multi-threaded points: {}", multiThreaded);

            Map<String, List<GraphPoint>> result = new HashMap<>();
            result.put("singleThreaded", singleThreaded);
            result.put("multiThreaded", multiThreaded);

            return ResponseEntity.ok(result);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error fetching graph data", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
