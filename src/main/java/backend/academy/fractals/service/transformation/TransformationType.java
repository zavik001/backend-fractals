package backend.academy.fractals.service.transformation;

public enum TransformationType {
    AFFINE,
    DISK,
    EXPONENTIAL,
    HEART,
    HYPERBOLIC,
    LINEAR,
    POLAR,
    SINE,
    SPHERE;

    public static TransformationType fromString(String name) {
        try {
            return TransformationType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown transformation type: " + name, e);
        }
    }
}
