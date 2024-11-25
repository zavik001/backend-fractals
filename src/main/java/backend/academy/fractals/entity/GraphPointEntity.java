package backend.academy.fractals.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("GraphPoint")
public class GraphPointEntity {
    @Id
    private String id;

    @Indexed
    private String generatorType;
    private int iterations;
    private long timeTaken;
}
