package backend.academy.fractals.service.transformation;

import backend.academy.fractals.service.model.Point;

public class DiskTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double sqrt = Math.sqrt(x * x + y * y);
        double newX = 1 / Math.PI * Math.atan(y / x) * Math.sin(Math.PI * sqrt);
        double newY = 1 / Math.PI * Math.atan(y / x) * Math.cos(Math.PI * sqrt);
        return new Point(newX, newY);
    }
}
