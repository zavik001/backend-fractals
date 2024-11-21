package backend.academy.fractals.service.transformation;

import backend.academy.fractals.service.model.Point;

public class HorseshoeTransformation implements Transformation {
    @Override
    public Point transform(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double newX = (point.x() - point.y()) * (point.x() + point.y()) / r;
        double newY = 2 * point.x() * point.y() / r;

        return new Point(newX, newY, point.color());
    }
}
