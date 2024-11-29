package backend.academy.fractals.service.image.storing;

import backend.academy.fractals.service.model.FractalImage;
import backend.academy.fractals.service.utils.ImageUtils;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class FormatImageSaver implements ImageSaver {

    private final ImageFormat format;

    @SneakyThrows
    @Override
    public void save(FractalImage image, Path path) {
        ImageIO.write(ImageUtils.convertFractalImageToBufferedImage(image), format.formatName(), path.toFile());
    }
}
