package backend.academy.fractals.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Point {
    private final double x;
    private final double y;
    private int color;

    public int getPixelX(int width) {
        return (int) ((x + 1) / 2 * width);
    }

    public int getPixelY(int height) {
        return (int) ((y + 1) / 2 * height);
    }
}
