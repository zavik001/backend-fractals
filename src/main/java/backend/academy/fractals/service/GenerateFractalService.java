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
import backend.academy.fractals.service.transformation.TransformationType;
import backend.academy.fractals.service.utils.FractalUtils;
import java.io.IOException;
import java.nio.file.Files;
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
    public CompletableFuture<Path> generateFractal(FractalRequest request,
            TransformationType transformationType, ImageType imageType) {

        return CompletableFuture.supplyAsync(() -> {
            FractalEntity fractalEntity = initializeFractalEntity(request, imageType, transformationType);
            try {
                Path outputPath = processFractalGeneration(request, imageType, transformationType);
                fractalEntity.imagePath(outputPath.toString());
                finalizeFractalEntity(fractalEntity, null);
                return outputPath;
            } catch (Exception ex) {
                handleFractalGenerationError(fractalEntity, ex);
                throw new RuntimeException("Error generating fractal: " + ex.getMessage(), ex);
            }
        });
    }

    private FractalEntity initializeFractalEntity(FractalRequest request, ImageType imageType,
            TransformationType transformationType) {

        FractalEntity entity = new FractalEntity();
        entity.startTime(LocalDateTime.now())
                .width(request.width())
                .height(request.height())
                .iterations(request.iterations())
                .symmetry(request.symmetry())
                .gamma(request.gamma())
                .transformationType(transformationType.name())
                .imageType(imageType.name())
                .generatorType(request.generatorType());
        return entity;
    }

    private Path processFractalGeneration(FractalRequest request, ImageType imageType,
            TransformationType transformationType) {

        String uniqueFileName = generateUniqueFileName(imageType);
        if (uniqueFileName == null || uniqueFileName.isBlank()) {
            throw new IllegalArgumentException("Generated file name is invalid (null or blank).");
        }

        Path outputPath = Path.of("images", uniqueFileName);
        try {
            Path parentDir = outputPath.getParent();
            if (parentDir == null) {
                throw new IllegalArgumentException("Parent directory is null for path: " + outputPath);
            }

            Files.createDirectories(parentDir);
            log.info("Directory ensured: {}", parentDir);
        } catch (IOException e) {
            log.error("Failed to create directories for path: {}", outputPath, e);
            throw new RuntimeException("Failed to create output directory", e);
        }

        log.info("Output path: {}", outputPath);

        FractalParameters parameters = new FractalParameters(
                request.width(),
                request.height(),
                request.iterations(),
                request.gamma(),
                request.symmetry(),
                transformationType);

        FractalGeneration generator = FractalGeneratorFactory.createGenerator(request.generatorType());
        List<Point> points = generator.generate(parameters);

        FractalUtils.applyGammaCorrection(points, request.gamma());

        ImageExporter imageExporter = ImageExporterFactory.createExporter(imageType);
        imageExporter.export(points, request.width(), request.height(), outputPath.toString());

        return outputPath;
    }

    private void handleFractalGenerationError(FractalEntity fractalEntity, Exception ex) {
        String errorMessage = "Error generating : " + ex.getMessage();
        fractalEntity.endTime(LocalDateTime.now())
                .errorMessage(errorMessage);

        log.error(errorMessage, ex);
        fractalRepository.save(fractalEntity);
    }

    private void finalizeFractalEntity(FractalEntity entity, String errorMessage) {
        entity.endTime(LocalDateTime.now())
                .errorMessage(errorMessage);
        fractalRepository.save(entity);
    }

    private String generateUniqueFileName(ImageType imageType) {
        String uniqueID = UUID.randomUUID().toString();
        return "fractal_" + uniqueID + "." + imageType.name().toLowerCase();
    }
}
