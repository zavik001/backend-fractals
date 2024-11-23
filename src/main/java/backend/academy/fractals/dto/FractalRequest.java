package backend.academy.fractals.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FractalRequest {

    @JsonProperty("width")
    @Positive(message = "Width must be positive")
    private int width;

    @JsonProperty("height")
    @Positive(message = "Height must be positive")
    private int height;

    @JsonProperty("iterations")
    @Positive(message = "Iterations must be positive")
    private int iterations;

    @JsonProperty("symmetry")
    @Positive(message = "Symmetry must be positive")
    private int symmetry;

    @JsonProperty("gamma")
    @DecimalMin(value = "0.0", inclusive = false, message = "Gamma must be positive")
    private double gamma;

    @JsonProperty("generatorType")
    @Pattern(regexp = "single-threaded|multi-threaded", message = "Invalid generator type")
    private String generatorType;

    @JsonProperty("transformationType")
    @Pattern(regexp = "LINEAR|SINE|SWIRL|SPHERE|HORSESHOE", message = "Invalid transformation type")
    private String transformationType;

    @JsonProperty("imageType")
    @Pattern(regexp = "PNG|JPG", message = "Invalid image type")
    private String imageType;
}
