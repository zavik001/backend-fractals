package backend.academy.fractals.service.generation;

import backend.academy.fractals.service.model.AffineCoefficient;
import backend.academy.fractals.service.model.BoundingBox;
import backend.academy.fractals.service.model.FractalImage;
import backend.academy.fractals.service.model.Pixel;
import backend.academy.fractals.service.model.Point;
import backend.academy.fractals.service.transformation.AffineTransformation;
import backend.academy.fractals.service.transformation.Transformation;
import backend.academy.fractals.service.utils.BoundingBoxUtil;
import backend.academy.fractals.service.utils.ImageUtil;
import backend.academy.fractals.service.utils.ListUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractGenerator implements Generator {

    private static final int STEPS_FOR_NORMALIZATION = 20;

    protected final int affineCount;
    protected final int samples;
    protected final int iterPerSample;
    protected final int symmetry;
    protected final List<Transformation> variations;

    public FractalImage generate(int width, int height, BoundingBox world) {
        FractalImage image = FractalImage.create(width, height);
        List<AffineTransformation> affineTransformations = generateAffineTransformations();
        renderAllImage(image, world, affineTransformations);
        return image;
    }

    public abstract void renderAllImage(
            FractalImage image,
            BoundingBox world,
            List<? extends AffineTransformation> affineTransformations);

    private List<AffineTransformation> generateAffineTransformations() {
        List<AffineTransformation> affineTransformations = new ArrayList<>();
        for (int i = 0; i < affineCount; i++) {
            AffineTransformation transformation = new AffineTransformation(
                    AffineCoefficient.generateRandom(ThreadLocalRandom.current()));
            affineTransformations.add(transformation);
        }
        return affineTransformations;
    }

    protected void renderOneSample(FractalImage image, BoundingBox world,
            List<? extends AffineTransformation> affineTransformations) {

        Point currentPoint = BoundingBoxUtil.randomPoint(world);
        for (int step = -STEPS_FOR_NORMALIZATION; step < iterPerSample; ++step) {
            AffineTransformation affine = ListUtil.random(affineTransformations);
            Transformation variation = ListUtil.random(variations);
            currentPoint = affine.apply(currentPoint);
            currentPoint = variation.apply(currentPoint);
            if (step > 0) {
                double theta = 0.0;
                for (int chunk = 0; chunk < symmetry; theta += Math.PI * 2 / symmetry, ++chunk) {
                    var point = BoundingBoxUtil.rotatePoint(world, currentPoint, theta);
                    processPoint(world, image, point, affine);
                }
            }
        }
    }

    private void processPoint(BoundingBox world, FractalImage image, Point point, AffineTransformation affine) {
        if (!world.contains(point)) {
            return;
        }
        Pixel pixel = ImageUtil.resolvePixel(world, point, image);
        if (pixel != null) {
            synchronized (pixel) {
                Color color = affine.affineCoefficient().color();
                pixel.saturateHitCount(color);
            }
        }
    }
}
