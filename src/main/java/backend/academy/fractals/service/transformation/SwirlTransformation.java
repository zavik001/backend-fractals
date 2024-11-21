package backend.academy.fractals.service.transformation;

import backend.academy.fractals.service.model.Point;

public class SwirlTransformation implements Transformation {
    @Override
    public Point transform(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double sinR = Math.sin(r);
        double cosR = Math.cos(r);

        double newX = point.x() * sinR - point.y() * cosR;
        double newY = point.x() * cosR + point.y() * sinR;

        return new Point(newX, newY, point.color());
    }
}
