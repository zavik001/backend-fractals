package backend.academy.fractals.service.utils;

import backend.academy.fractals.service.model.Point;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalUtils {

    private static final int MAX_COLOR_VALUE = 0xFFFFFF;
    private static final int COLOR_COMPONENT_MASK = 0xFF;
    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;
    private static final int BLUE_SHIFT = 0;
    private static final double LOG_BASE = 2.0;

    public static void applyGammaCorrection(List<Point> points, double gamma) {
        if (gamma <= 0) {
            throw new IllegalArgumentException("Gamma value must be greater than 0");
        }

        Map<Point, Integer> histogram = new ConcurrentHashMap<>();

        points.parallelStream().forEach(point ->
            histogram.merge(point, 1, Integer::sum)
        );

        int maxIntensity = histogram.values().stream().max(Integer::compareTo).orElse(1);

        points.parallelStream().forEach(point -> {
            int intensity = histogram.getOrDefault(point, 0);

            double normalizedIntensity = (double) intensity / maxIntensity;

            double correctedIntensity = Math.pow(
                    Math.log(1 + normalizedIntensity) / Math.log(LOG_BASE),
                    gamma);

            int colorValue = (int) (correctedIntensity * MAX_COLOR_VALUE);

            int red = (colorValue >> RED_SHIFT) & COLOR_COMPONENT_MASK;
            int green = (colorValue >> GREEN_SHIFT) & COLOR_COMPONENT_MASK;
            int blue = (colorValue >> BLUE_SHIFT) & COLOR_COMPONENT_MASK;

            point.color((red << RED_SHIFT) | (green << GREEN_SHIFT) | blue);
        });
    }
}
