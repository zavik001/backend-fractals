package backend.academy.fractals.service.transformation;

import backend.academy.fractals.service.model.Point;

public class SineTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        return new Point(Math.sin(point.x()), Math.sin(point.y()));
    }
}
