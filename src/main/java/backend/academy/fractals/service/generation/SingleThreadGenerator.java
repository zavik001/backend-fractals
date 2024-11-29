package backend.academy.fractals.service.generation;

import backend.academy.fractals.service.model.BoundingBox;
import backend.academy.fractals.service.model.FractalImage;
import backend.academy.fractals.service.transformation.AffineTransformation;
import backend.academy.fractals.service.transformation.Transformation;
import java.util.List;

public class SingleThreadGenerator extends AbstractGenerator {

    public SingleThreadGenerator(
        int affineCount,
        int samples,
        int iterPerSample,
        int symmetry,
        List<Transformation> variations
    ) {
        super(affineCount, samples, iterPerSample, symmetry, variations);
    }

    @Override
    public void renderAllImage(
        FractalImage image,
        BoundingBox world,
        List<? extends AffineTransformation> affineTransformations
    ) {
        for (int i = 0; i < samples; i++) {
            renderOneSample(image, world, affineTransformations);
        }
    }
}
