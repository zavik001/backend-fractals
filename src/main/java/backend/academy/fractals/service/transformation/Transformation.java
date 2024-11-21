package backend.academy.fractals.service.transformation;

import backend.academy.fractals.service.model.Point;

public interface Transformation {
    Point transform(Point point);
}
