package backend.academy.fractals.service.image.modifier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageProcessorType {
    GAMMA_CORRECTION("GammaCorrection");

    private final String typeName;

    public static ImageProcessorType fromString(String name) {
        for (ImageProcessorType type : values()) {
            if (type.typeName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown processor type: " + name);
    }
}

