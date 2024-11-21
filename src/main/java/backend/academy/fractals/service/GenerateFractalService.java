package backend.academy.fractals.service;

import backend.academy.fractals.dto.FractalRequest;
import backend.academy.fractals.entity.FractalEntity;
import backend.academy.fractals.repository.FractalRepository;
import backend.academy.fractals.service.generation.FractalGeneration;
import backend.academy.fractals.service.generation.factory.FractalGeneratorFactory;
import backend.academy.fractals.service.image.ImageExporter;
import backend.academy.fractals.service.image.ImageType;
import backend.academy.fractals.service.image.factory.ImageExporterFactory;
import backend.academy.fractals.service.model.FractalParameters;
import backend.academy.fractals.service.model.Point;
import backend.academy.fractals.service.utils.FractalUtils;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class GenerateFractalService {

    private final FractalRepository fractalRepository;

    @Async("taskExecutor")
    public CompletableFuture<Path> generateFractal(FractalRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            FractalEntity fractalEntity = initializeFractalEntity(request);
            try {
                Path outputPath = processFractalGeneration(request);
                fractalEntity.imagePath(outputPath.toString());
                finalizeFractalEntity(fractalEntity, null);
                return outputPath;
            } catch (Exception ex) {
                handleFractalGenerationError(fractalEntity, ex);
                throw new RuntimeException("Error generating fractal: " + ex.getMessage(), ex);
            }
        });
    }

    private FractalEntity initializeFractalEntity(FractalRequest request) {
        FractalEntity entity = new FractalEntity();
        entity.startTime(LocalDateTime.now());
        entity.width(request.width());
        entity.height(request.height());
        entity.iterations(request.iterations());
        entity.symmetry(request.symmetry());
        entity.gamma(request.gamma());
        entity.transformationType(request.transformationType().name());
        entity.imageType(request.imageType().name());
        entity.generatorType(request.generatorType());
        return entity;
    }

    private Path processFractalGeneration(FractalRequest request) {
        Path outputPath = Path.of("images", generateUniqueFileName(request.imageType()));

        FractalParameters parameters = new FractalParameters(
                request.width(),
                request.height(),
                request.iterations(),
                request.gamma(),
                request.symmetry(),
                request.transformationType());

        FractalGeneration generator = FractalGeneratorFactory.createGenerator(request.generatorType());
        List<Point> points = generator.generate(parameters);

        FractalUtils.applyGammaCorrection(points, request.gamma());

        ImageExporter imageExporter = ImageExporterFactory.createExporter(request.imageType());
        imageExporter.export(points, request.width(), request.height(), outputPath.toString());

        return outputPath;
    }

    private void handleFractalGenerationError(FractalEntity fractalEntity, Exception ex) {
        String errorMessage = "Error generating : " + ex.getMessage();
        fractalEntity.endTime(LocalDateTime.now());
        fractalEntity.errorMessage(errorMessage);

        log.error(errorMessage, ex);
        fractalRepository.save(fractalEntity);
    }

    private void finalizeFractalEntity(FractalEntity entity, String errorMessage) {
        entity.endTime(LocalDateTime.now());
        entity.errorMessage(errorMessage);
        fractalRepository.save(entity);
    }

    private String generateUniqueFileName(ImageType imageType) {
        String uniqueID = UUID.randomUUID().toString();
        return "fractal_" + uniqueID + "." + imageType.name().toLowerCase();
    }
}
