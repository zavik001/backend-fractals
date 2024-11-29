package backend.academy.fractals.service.utils;

import backend.academy.fractals.service.model.BoundingBox;
import backend.academy.fractals.service.model.FractalImage;
import backend.academy.fractals.service.model.Pixel;
import backend.academy.fractals.service.model.Point;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageUtil {

    public static Pixel resolvePixel(BoundingBox rect, Point point, FractalImage image) {
        if (!rect.contains(point)) {
            return null;
        }
        return image.pixel(
            (int) ((point.x() - rect.left()) / rect.horizontalSize() * image.width()),
            (int) ((point.y() - rect.top()) / rect.verticalSize() * image.height())
        );
    }
}
