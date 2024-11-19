package backend.academy.fractals.service.transformation;

import backend.academy.fractals.service.model.Point;

public class SineTransformation implements Transformation {
    @Override
    public Point transform(Point point) {
        double newX = Math.sin(point.x());
        double newY = Math.sin(point.y());
        return new Point(newX, newY, point.color());
    }
}
