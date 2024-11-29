package backend.academy.fractals.service.generation;

import backend.academy.fractals.service.model.BoundingBox;
import backend.academy.fractals.service.model.FractalImage;

@FunctionalInterface
public interface Generator {
    FractalImage generate(int width, int height, BoundingBox world);
}
