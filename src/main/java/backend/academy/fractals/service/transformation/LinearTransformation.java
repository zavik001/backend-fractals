package backend.academy.fractals.service.transformation;

import backend.academy.fractals.service.model.Point;

public class LinearTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        return point;
    }
}
