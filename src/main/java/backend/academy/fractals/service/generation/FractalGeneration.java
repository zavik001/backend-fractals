package backend.academy.fractals.service.generation;

import backend.academy.fractals.service.model.FractalParameters;
import backend.academy.fractals.service.model.Point;
import java.util.List;

public interface FractalGeneration {
    List<Point> generate(FractalParameters parameters);
}
