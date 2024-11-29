package backend.academy.fractals.controller;

import backend.academy.fractals.dto.FractalRequest;
import backend.academy.fractals.service.GenerateFractalService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fractals")
public class FractalController {

    private final GenerateFractalService generateFractalService;

    @PostMapping("/generate")
    public CompletableFuture<ResponseEntity<byte[]>> generateFractal(@Valid @RequestBody FractalRequest request) {
        log.info("Received request: {}", request);

        return generateFractalService.generateFractal(request)
                .thenApply(filePath -> {
                    try {
                        if (!Files.exists(filePath)) {
                            log.error("Generated file not found: {}", filePath);
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new byte[0]);
                        }

                        byte[] imageBytes = Files.readAllBytes(filePath);
                        log.info("Successfully read generated fractal image: {}", filePath);

                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION,
                                        "attachment; filename=\"" + filePath.getFileName() + "\"")
                                .contentType(MediaType.IMAGE_PNG)
                                .body(imageBytes);
                    } catch (IOException e) {
                        log.error("Error reading generated file: {}", filePath, e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new byte[0]);
                    }
                })
                .exceptionally(ex -> {
                    log.error("Error generating fractal", ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new byte[0]);
                });
    }
}
