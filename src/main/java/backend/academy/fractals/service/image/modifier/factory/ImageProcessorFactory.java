package backend.academy.fractals.service.image.modifier.factory;

import backend.academy.fractals.service.image.modifier.GammaCorrection;
import backend.academy.fractals.service.image.modifier.ImageProcessor;
import backend.academy.fractals.service.image.modifier.ImageProcessorType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageProcessorFactory {

    public static ImageProcessor createProcessor(ImageProcessorType type, double gamma) {
        return switch (type) {
            case GAMMA_CORRECTION -> new GammaCorrection(gamma);
        };
    }
}
