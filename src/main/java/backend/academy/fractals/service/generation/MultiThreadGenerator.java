package backend.academy.fractals.service.generation;

import backend.academy.fractals.service.model.BoundingBox;
import backend.academy.fractals.service.model.FractalImage;
import backend.academy.fractals.service.transformation.AffineTransformation;
import backend.academy.fractals.service.transformation.Transformation;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;

public class MultiThreadGenerator extends AbstractGenerator {

    public MultiThreadGenerator(
            int affineCount,
            int samples,
            int iterPerSample,
            int symmetry,
            List<Transformation> variations) {
        super(affineCount, samples, iterPerSample, symmetry, variations);
    }

    @SneakyThrows
    @Override
    public void renderAllImage(
            FractalImage image,
            BoundingBox world,
            List<? extends AffineTransformation> affineTransformations) {
        var executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < samples; i++) {
            executorService.execute(
                    () -> renderOneSample(image, world, affineTransformations));
        }
        executorService.shutdown();
        executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }
}
