package backend.academy.fractals.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphPoint {
    @JsonProperty("iterations")
    private int iterations;

    @JsonProperty("timeTaken")
    private long timeTaken;
}
