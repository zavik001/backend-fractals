package backend.academy.fractals.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.Data;
import org.jspecify.annotations.NonNull;

@Data
public class FractalRequest {

    @JsonProperty("width")
    @Positive(message = "Width must be positive")
    private int width;

    @JsonProperty("height")
    @Positive(message = "Height must be positive")
    private int height;

    @JsonProperty("left")
    private double left;

    @JsonProperty("top")
    private double top;

    @JsonProperty("horizontalSize")
    private double horizontalSize;

    @JsonProperty("verticalSize")
    private double verticalSize;

    @JsonProperty("affineCoefficients")
    private int affineCoefficients;

    @JsonProperty("samples")
    @Min(value = 1, message = "Samples must be at least 1")
    @Max(value = 10, message = "Samples cannot exceed 10")
    private int samples;

    @JsonProperty("iterPerSample")
    @Min(value = 1, message = "Iterations per sample must be at least 1")
    @Max(value = 1_000_000_000, message = "Iterations per sample cannot exceed 1 billion")
    private int iterPerSample;

    @JsonProperty("symmetry")
    @Positive(message = "Symmetry must be positive")
    private int symmetry;

    @JsonProperty("gamma")
    @DecimalMin(value = "0.0", inclusive = false, message = "Gamma must be positive")
    private double gamma;

    @JsonProperty("generatorType")
    @Pattern(regexp = "single-threaded|multi-threaded", message = "Invalid generator type")
    private String generatorType;

    @JsonProperty("transformations")
    @Valid
    private List<String> transformations;

    @NonNull
    @JsonProperty("ImageProcessors")
    @Valid
    private List<String> imageProcessors;

    @JsonProperty("imageType")
    @Pattern(regexp = "PNG|JPG|BMP", message = "Invalid image type")
    private String imageType;
}
