package backend.academy.fractals.service.generation;

import backend.academy.fractals.service.model.FractalParameters;
import backend.academy.fractals.service.model.Point;
import backend.academy.fractals.service.transformation.Transformation;
import backend.academy.fractals.service.transformation.factory.TransformationFactory;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class SingleThreadFractalGenerator implements FractalGeneration {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public List<Point> generate(FractalParameters parameters) {
        Transformation transformation = TransformationFactory.createTransformation(parameters.transformationType());
        int symmetry = parameters.symmetry();
        int iterations = parameters.iterations();

        List<Point> points = new ArrayList<>(iterations * (symmetry > 0 ? symmetry : 1));

        for (int i = 0; i < iterations; i++) {
            Point point = new Point(-1 + 2 * RANDOM.nextDouble(), -1 + 2 * RANDOM.nextDouble(), 0);

            Point transformedPoint = transformation.transform(point);
            points.add(transformedPoint);

            if (symmetry > 0) {
                for (int k = 1; k < symmetry; k++) {
                    double angle = 2 * Math.PI * k / symmetry;
                    double newX = point.x() * Math.cos(angle) - point.y() * Math.sin(angle);
                    double newY = point.x() * Math.sin(angle) + point.y() * Math.cos(angle);
                    points.add(new Point(newX, newY, 0));
                }
            }
        }

        return points;
    }
}
