package backend.academy.fractals.dto;

import backend.academy.fractals.service.image.ImageType;
import backend.academy.fractals.service.transformation.TransformationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FractalRequest {

    @Positive(message = "Width must be positive")
    private int width;

    @Positive(message = "Height must be positive")
    private int height;

    @Positive(message = "Iterations must be positive")
    private int iterations;

    @Positive(message = "Symmetry must be positive")
    private int symmetry;

    @DecimalMin(value = "0.0", inclusive = false, message = "Gamma must be positive")
    private double gamma;

    @Pattern(regexp = "single-threaded|multi-threaded", message = "Invalid generator type")
    private String generatorType;

    @Pattern(regexp = "LINEAR|SINE|SWIRL|SPHERE|HORSESHOE", message = "Invalid transformation type")
    private TransformationType transformationType;

    @Pattern(regexp = "PNG|JPG", message = "Invalid image type")
    private ImageType imageType;
}
