package backend.academy.fractals.service;

import backend.academy.fractals.dto.FractalRequest;
import backend.academy.fractals.entity.FractalEntity;
import backend.academy.fractals.repository.jpa.FractalRepository;
import backend.academy.fractals.service.generation.Generator;
import backend.academy.fractals.service.generation.GeneratorFactory;
import backend.academy.fractals.service.image.modifier.ImageProcessor;
import backend.academy.fractals.service.image.modifier.ImageProcessorType;
import backend.academy.fractals.service.image.modifier.factory.ImageProcessorFactory;
import backend.academy.fractals.service.image.storing.ImageFormat;
import backend.academy.fractals.service.image.storing.ImageSaver;
import backend.academy.fractals.service.image.storing.factory.ImageFormatFactory;
import backend.academy.fractals.service.model.BoundingBox;
import backend.academy.fractals.service.model.FractalImage;
import backend.academy.fractals.service.model.redis.GraphPoint;
import backend.academy.fractals.service.transformation.Transformation;
import backend.academy.fractals.service.transformation.factory.TransformationFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateFractalService {

    private final FractalRepository fractalRepository;
    private final FractalRedisService redisService;

    @Async("taskExecutor")
    public CompletableFuture<Path> generateFractal(FractalRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            FractalEntity fractalEntity = initializeFractalEntity(request);
            try {
                Path filePath = processFractalGeneration(fractalEntity, request);
                finalizeFractalEntity(fractalEntity, null);
                return filePath;
            } catch (Exception ex) {
                handleFractalGenerationError(fractalEntity, ex);
                throw new RuntimeException("Error generating fractal: " + ex.getMessage(), ex);
            }
        });
    }

    private FractalEntity initializeFractalEntity(FractalRequest request) {
        FractalEntity entity = FractalEntity.builder()
                .generatorType(request.generatorType())
                .startTime(LocalDateTime.now())
                .width(request.width())
                .height(request.height())
                .transformations(String.join(",", request.transformations()))
                .build();
        return fractalRepository.save(entity);
    }

    private Path processFractalGeneration(FractalEntity fractalEntity, FractalRequest request) {
        ImageFormat imageFormat = ImageFormatFactory.parse(request.imageType());
        String uniqueFileName = "fractal_" + UUID.randomUUID() + "." + imageFormat.formatName();
        Path outputPath = Path.of("images", uniqueFileName);

        try {
            Files.createDirectories(outputPath.getParent());
            log.info("Directory ensured: {}", outputPath.getParent());
        } catch (Exception ex) {
            log.error("Failed to create directories for path: {}", outputPath, ex);
            throw new RuntimeException("Failed to create output directory", ex);
        }

        BoundingBox area = new BoundingBox(
                request.left(),
                request.top(),
                request.horizontalSize(),
                request.verticalSize());

        List<Transformation> transformations = TransformationFactory.createAll(request.transformations());

        Generator generator = GeneratorFactory.createGenerator(
                request.generatorType(),
                request.affineCoefficients(),
                request.samples(),
                request.iterPerSample(),
                request.symmetry(),
                transformations);

        FractalImage image = generator.generate(request.width(), request.height(), area);

        List<ImageProcessor> processors = request.imageProcessors().stream()
                .map(ImageProcessorType::fromString)
                .map(type -> ImageProcessorFactory.createProcessor(type, request.gamma()))
                .toList();

        processors.forEach(processor -> processor.process(image));

        fractalEntity.endTime(LocalDateTime.now());
        fractalRepository.save(fractalEntity);

        long duration = Duration.between(fractalEntity.startTime(), fractalEntity.endTime()).toMillis();
        redisService.addPoint(request.generatorType(), new GraphPoint(request.iterPerSample(), duration));

        ImageSaver saver = ImageFormatFactory.createSaver(imageFormat);
        saver.save(image, outputPath);

        return outputPath;
    }

    private void handleFractalGenerationError(FractalEntity fractalEntity, Exception ex) {
        fractalEntity.errorMessage("Error: " + ex.getMessage());
        fractalRepository.save(fractalEntity);
    }

    private void finalizeFractalEntity(FractalEntity entity, String errorMessage) {
        entity.errorMessage(errorMessage);
        fractalRepository.save(entity);
    }
}
