package backend.academy.fractals.service.image;

import backend.academy.fractals.service.model.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpgImageExporter implements ImageExporter {

    @Override
    public void export(List<Point> points, int width, int height, String outputFile) {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (Point point : points) {
                int x = point.getPixelX(width);
                int y = point.getPixelY(height);
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    image.setRGB(x, y, point.color());
                }
            }

            Path outputPath = Path.of(outputFile);
            log.info("Exporting image to: {}", outputPath);
            ImageIO.write(image, "jpg", outputPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Error while exporting image", e);
        }
    }
}
