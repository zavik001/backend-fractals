package backend.academy.fractals.service.utils;

import backend.academy.fractals.service.model.BoundingBox;
import backend.academy.fractals.service.model.Point;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BoundingBoxUtil {

    public static Point randomPoint(BoundingBox rect) {
        return new Point(
                rect.left() + ThreadLocalRandom.current().nextDouble() * rect.horizontalSize(),
                rect.top() + Math.random() * rect.verticalSize()
        );
    }

    public static Point rotatePoint(BoundingBox rect, Point point, double angle) {
        double centerX = rect.left() + rect.horizontalSize() / 2;
        double centerY = rect.top() + rect.verticalSize() / 2;
        double x = (point.x() - centerX) * Math.cos(angle)
                - (point.y() - centerY) * Math.sin(angle) + centerX;
        double y = (point.x() - centerX) * Math.sin(angle)
                + (point.y() - centerY) * Math.cos(angle) + centerY;
        return new Point(x, y);
    }
}
