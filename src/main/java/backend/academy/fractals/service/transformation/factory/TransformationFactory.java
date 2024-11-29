package backend.academy.fractals.service.transformation.factory;

import backend.academy.fractals.service.transformation.DiskTransformation;
import backend.academy.fractals.service.transformation.ExponentialTransformation;
import backend.academy.fractals.service.transformation.HeartTransformation;
import backend.academy.fractals.service.transformation.HyperbolicTransformation;
import backend.academy.fractals.service.transformation.LinearTransformation;
import backend.academy.fractals.service.transformation.PolarTransformation;
import backend.academy.fractals.service.transformation.SineTransformation;
import backend.academy.fractals.service.transformation.SphereTransformation;
import backend.academy.fractals.service.transformation.Transformation;
import backend.academy.fractals.service.transformation.TransformationType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransformationFactory {

    public static Transformation create(TransformationType type) {
        return switch (type) {
            case DISK -> new DiskTransformation();
            case EXPONENTIAL -> new ExponentialTransformation();
            case HEART -> new HeartTransformation();
            case HYPERBOLIC -> new HyperbolicTransformation();
            case LINEAR -> new LinearTransformation();
            case POLAR -> new PolarTransformation();
            case SINE -> new SineTransformation();
            case SPHERE -> new SphereTransformation();
            default -> throw new IllegalArgumentException("Unknown transformation type: " + type);
        };
    }

    public static List<Transformation> createAll(List<String> types) {
        return types.stream()
                .map(TransformationType::fromString)
                .map(TransformationFactory::create)
                .collect(Collectors.toList());
    }
}
