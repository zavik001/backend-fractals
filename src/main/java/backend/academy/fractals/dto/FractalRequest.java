package backend.academy.fractals.dto;

import backend.academy.fractals.service.image.ImageType;
import backend.academy.fractals.service.transformation.TransformationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FractalRequest {
    private int width;
    private int height;
    private int iterations;
    private int symmetry;
    private double gamma;
    private String generatorType;
    private TransformationType transformationType;
    private ImageType imageType;
}
