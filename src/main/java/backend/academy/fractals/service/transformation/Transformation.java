package backend.academy.fractals.service.transformation;

import backend.academy.fractals.service.model.Point;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {
}
