package backend.academy.fractals.service.image.modifier;

import backend.academy.fractals.service.model.FractalImage;

@FunctionalInterface
public interface ImageProcessor {
    void process(FractalImage image);
}
