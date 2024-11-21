package backend.academy.fractals.service.image.factory;

import backend.academy.fractals.service.image.ImageExporter;
import backend.academy.fractals.service.image.ImageType;
import backend.academy.fractals.service.image.JpgImageExporter;
import backend.academy.fractals.service.image.PngImageExporter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageExporterFactory {
    public static ImageExporter createExporter(ImageType type) {
        switch (type) {
            case PNG:
                return new PngImageExporter();
            case JPG:
                return new JpgImageExporter();
            default:
                throw new IllegalArgumentException("Unsupported image type: " + type);
        }
    }
}
