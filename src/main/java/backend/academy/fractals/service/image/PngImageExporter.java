package backend.academy.fractals.service.image;

import backend.academy.fractals.service.model.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PngImageExporter implements ImageExporter {
    private static final int DEFAULT_COLOR = 0xFFFFFFFF;
    private static final int ALPHA_CHANNEL_MASK = 0xFF000000;
    private static final int MAX_COLOR_VALUE = 0xFFFFFF;

    @Override
    public void export(List<Point> points, int width, int height, String outputFile) {
        if (outputFile == null || outputFile.isBlank()) {
            throw new IllegalArgumentException("Output file path cannot be null or blank.");
        }

        try {
            Path outputPath = Path.of(outputFile);

            Path parentDir = outputPath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
                log.info("Created directories for path: {}", parentDir);
            }

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (Point point : points) {
                int x = point.getPixelX(width);
                int y = point.getPixelY(height);
                int color = point.color();

                if (color < 0 || color > MAX_COLOR_VALUE) {
                    color = DEFAULT_COLOR;
                } else {
                    color = ALPHA_CHANNEL_MASK | color;
                }

                if (x >= 0 && x < width && y >= 0 && y < height) {
                    image.setRGB(x, y, color);
                }
            }

            log.info("Exporting PNG image to: {}", outputPath);
            ImageIO.write(image, "png", outputPath.toFile());
        } catch (IOException e) {
            log.error("Failed to export PNG image to file: {}", outputFile, e);
            throw new RuntimeException("Failed to export PNG image", e);
        }
    }
}
