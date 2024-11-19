package backend.academy.fractals.service.image;

import backend.academy.fractals.service.model.Point;
import java.util.List;

public interface ImageExporter {
    void export(List<Point> points, int width, int height, String outputFile);
}
