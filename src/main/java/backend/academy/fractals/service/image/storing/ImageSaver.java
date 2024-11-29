package backend.academy.fractals.service.image.storing;

import backend.academy.fractals.service.model.FractalImage;
import java.nio.file.Path;

public interface ImageSaver {
    void save(FractalImage image, Path path);
}
