package backend.academy.fractals.service.image.storing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageFormat {
    PNG("png"),
    BMP("bmp"),
    JPG("jpg");

    private final String formatName;

    public static ImageFormat fromString(String name) {
        for (ImageFormat format : values()) {
            if (format.formatName.equalsIgnoreCase(name)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown image format: " + name);
    }
}
