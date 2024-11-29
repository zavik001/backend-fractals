package backend.academy.fractals.service.generation;

import backend.academy.fractals.service.transformation.Transformation;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GeneratorFactory {

    public static Generator createGenerator(
        String type,
        int affineCount,
        int samples,
        int iterPerSample,
        int symmetry,
        List<Transformation> variations
    ) {
        return switch (type.toLowerCase()) {
            case "single-threaded" -> new SingleThreadGenerator(
                affineCount,
                samples,
                iterPerSample,
                symmetry,
                variations
            );
            case "multi-threaded" -> new MultiThreadGenerator(
                affineCount,
                samples,
                iterPerSample,
                symmetry,
                variations
            );
            default -> throw new IllegalArgumentException("Unknown generator type: " + type);
        };
    }
}
