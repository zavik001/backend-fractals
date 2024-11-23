package backend.academy.fractals.controller;

import backend.academy.fractals.dto.FractalRequest;
import backend.academy.fractals.service.GenerateFractalService;
import backend.academy.fractals.service.image.ImageType;
import backend.academy.fractals.service.transformation.TransformationType;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RestController
@RequestMapping("/api/fractals")
public class FractalController {

    private final GenerateFractalService generateFractalService;

    @PostMapping("/generate")
    public CompletableFuture<ResponseEntity<byte[]>> generateFractal(@Valid @RequestBody FractalRequest request) {
        log.info("Received request: {}", request);

        ImageType imageType;
        TransformationType transformationType;

        try {
            imageType = ImageType.valueOf(request.imageType().toUpperCase());
            transformationType = TransformationType.valueOf(request.transformationType().toUpperCase());
        } catch (IllegalArgumentException ex) {
            log.error("Invalid enum value in request", ex);
            return CompletableFuture.completedFuture(
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new byte[0]));
        }

        return generateFractalService.generateFractal(request, transformationType, imageType)
                .thenApply(outputPath -> {
                    try {
                        if (!Files.exists(outputPath)) {
                            log.error("File not found: {}", outputPath);
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(new byte[0]);
                        }

                        byte[] imageBytes = Files.readAllBytes(outputPath);
                        log.info("Successfully read file: {}", outputPath);

                        MediaType mediaType = switch (imageType) {
                            case PNG -> MediaType.IMAGE_PNG;
                            case JPG -> MediaType.IMAGE_JPEG;
                        };

                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION,
                                        "attachment; filename=\"" + outputPath.getFileName() + "\"")
                                .contentType(mediaType)
                                .body(imageBytes);
                    } catch (IOException e) {
                        log.error("Error reading file: {}", outputPath, e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new byte[0]);
                    }
                })
                .exceptionally(ex -> {
                    log.error("Error generating fractal", ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new byte[0]);
                });
    }
}
