package backend.academy.fractals.service.transformation.factory;

import backend.academy.fractals.service.transformation.HorseshoeTransformation;
import backend.academy.fractals.service.transformation.LinearTransformation;
import backend.academy.fractals.service.transformation.SineTransformation;
import backend.academy.fractals.service.transformation.SphereTransformation;
import backend.academy.fractals.service.transformation.SwirlTransformation;
import backend.academy.fractals.service.transformation.Transformation;
import backend.academy.fractals.service.transformation.TransformationType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransformationFactory {
    public static Transformation createTransformation(TransformationType type) {
        Transformation transformation = null;
        switch (type) {
            case LINEAR:
                transformation = new LinearTransformation();
                break;
            case SINE:
                transformation = new SineTransformation();
                break;
            case SWIRL:
                transformation = new SwirlTransformation();
                break;
            case SPHERE:
                transformation = new SphereTransformation();
                break;
            case HORSESHOE:
                transformation = new HorseshoeTransformation();
                break;
            default:
                throw new IllegalArgumentException("Unknown transformation type: " + type);
        }

        return transformation;
    }
}
