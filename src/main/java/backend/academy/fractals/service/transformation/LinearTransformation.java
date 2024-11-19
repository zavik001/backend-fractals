package backend.academy.fractals.service.transformation;

import backend.academy.fractals.service.model.Point;

public class LinearTransformation implements Transformation {
    @Override
    public Point transform(Point point) {
        return new Point(point.x(), point.y(), point.color());
    }
}
