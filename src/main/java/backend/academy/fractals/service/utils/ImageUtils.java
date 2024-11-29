package backend.academy.fractals.service.utils;

import backend.academy.fractals.service.model.FractalImage;
import backend.academy.fractals.service.model.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageUtils {

    public static BufferedImage convertFractalImageToBufferedImage(FractalImage image) {
        BufferedImage renderedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.pixel(x, y);
                Color color = new Color(pixel.red(), pixel.green(), pixel.blue());
                renderedImage.setRGB(x, y, color.getRGB());
            }
        }
        return renderedImage;
    }
}
