package backend.academy.fractals.controller;

import backend.academy.fractals.dto.FractalRequest;
import backend.academy.fractals.service.GenerateFractalService;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/fractals")
public class FractalController {

    private final GenerateFractalService generateFractalService;

    @PostMapping("/generate")
    public CompletableFuture<ResponseEntity<byte[]>> generateFractal(@Validated @RequestBody FractalRequest request) {
        return generateFractalService.generateFractal(request)
                .thenApply(outputPath -> {
                    try {
                        byte[] imageBytes = Files.readAllBytes(outputPath);

                        MediaType mediaType = switch (request.imageType()) {
                            case PNG -> MediaType.IMAGE_PNG;
                            case JPG -> MediaType.IMAGE_JPEG;
                        };

                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION,
                                        "attachment; filename=\"" + outputPath.getFileName() + "\"")
                                .contentType(mediaType)
                                .body(imageBytes);
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new byte[0]);
                    }
                })
                .exceptionally(ex -> {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new byte[0]);
                });
    }
}
