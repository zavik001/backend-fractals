package backend.academy.fractals.service.transformation;

import backend.academy.fractals.service.model.Point;

public class SphereTransformation implements Transformation {
    @Override
    public Point transform(Point point) {
        double r2 = point.x() * point.x() + point.y() * point.y();
        double newX = point.x() / r2;
        double newY = point.y() / r2;

        return new Point(newX, newY, point.color());
    }
}
