package backend.academy.fractals.service.image.storing.factory;

import backend.academy.fractals.service.image.storing.FormatImageSaver;
import backend.academy.fractals.service.image.storing.ImageFormat;
import backend.academy.fractals.service.image.storing.ImageSaver;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageFormatFactory {

    public static ImageSaver createSaver(ImageFormat format) {
        return switch (format) {
            case PNG -> new FormatImageSaver(ImageFormat.PNG);
            case BMP -> new FormatImageSaver(ImageFormat.BMP);
            case JPG -> new FormatImageSaver(ImageFormat.JPG);
        };
    }

    public static ImageFormat parse(String formatName) {
        return ImageFormat.fromString(formatName);
    }
}
