package backend.academy.fractals.service.image;

import backend.academy.fractals.service.model.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;

public class JpgImageExporter implements ImageExporter {
    @Override
    public void export(List<Point> points, int width, int height, String outputFile) {
        if (outputFile == null || outputFile.isBlank()) {
            throw new IllegalArgumentException("Output file path cannot be null or blank.");
        }

        try {
            String sanitizedPath = FilenameUtils.getName(outputFile);
            Path outputPath = Paths.get(sanitizedPath).normalize();

            try {
                outputPath = outputPath.toRealPath();
            } catch (IOException e) {
                outputPath = outputPath.toAbsolutePath();
            }

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (Point point : points) {
                int x = point.getPixelX(width);
                int y = point.getPixelY(height);
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    image.setRGB(x, y, point.color());
                }
            }

            Path parentDir = outputPath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            ImageIO.write(image, "jpg", outputPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export image to: " + outputFile, e);
        }
    }
}
