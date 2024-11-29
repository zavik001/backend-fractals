package backend.academy.fractals.service.model;

public record BoundingBox(double left, double top, double horizontalSize, double verticalSize) {
    public boolean contains(Point p) {
        return p.x() >= left && p.x() < left + horizontalSize && p.y() >= top && p.y() < top + verticalSize;
    }
}
