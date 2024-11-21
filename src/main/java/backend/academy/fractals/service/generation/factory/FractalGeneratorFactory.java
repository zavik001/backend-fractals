package backend.academy.fractals.service.generation.factory;

import backend.academy.fractals.service.generation.FractalGeneration;
import backend.academy.fractals.service.generation.MultiThreadFractalGenerator;
import backend.academy.fractals.service.generation.SingleThreadFractalGenerator;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalGeneratorFactory {
    public static FractalGeneration createGenerator(String type) {
        switch (type) {
            case "single-threaded":
                return new SingleThreadFractalGenerator();
            case "multi-threaded":
                return new MultiThreadFractalGenerator();
            default:
                throw new IllegalArgumentException("Unknown generator type: " + type);
        }
    }
}
