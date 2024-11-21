package backend.academy.fractals.service.generation;

import backend.academy.fractals.service.model.FractalParameters;
import backend.academy.fractals.service.model.Point;
import backend.academy.fractals.service.transformation.Transformation;
import backend.academy.fractals.service.transformation.factory.TransformationFactory;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadFractalGenerator implements FractalGeneration {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public List<Point> generate(FractalParameters parameters) {
        ConcurrentLinkedQueue<Point> points = new ConcurrentLinkedQueue<>();

        Transformation transformation = TransformationFactory.createTransformation(parameters.transformationType());
        int symmetry = parameters.symmetry();
        int totalIterations = parameters.iterations();
        int iterationsPerThread = totalIterations / NUM_THREADS;

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(() -> {
                for (int j = 0; j < iterationsPerThread; j++) {
                    Point point = new Point(-1 + 2 * RANDOM.nextDouble(), -1 + 2 * RANDOM.nextDouble(), 0);
                    Point transformedPoint = transformation.transform(point);
                    points.add(transformedPoint);

                    if (symmetry > 0) {
                        for (int k = 1; k < symmetry; k++) {
                            double angle = 2 * Math.PI * k / symmetry;
                            double newX = transformedPoint.x() * Math.cos(angle) - transformedPoint.y()
                                * Math.sin(angle);
                            double newY = transformedPoint.x() * Math.sin(angle) + transformedPoint.y()
                                * Math.cos(angle);
                            points.add(new Point(newX, newY, 0));
                        }
                    }
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        return new ArrayList<>(points);
    }
}
