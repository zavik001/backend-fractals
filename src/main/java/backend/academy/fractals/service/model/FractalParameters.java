package backend.academy.fractals.service.model;

import backend.academy.fractals.service.transformation.TransformationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FractalParameters {
    private final int width;
    private final int height;
    private final int iterations;
    private final double gamma;
    private final int symmetry;
    private final TransformationType transformationType;
}
