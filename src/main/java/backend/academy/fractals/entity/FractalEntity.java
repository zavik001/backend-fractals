package backend.academy.fractals.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fractal_generations", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FractalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "generator_type", nullable = false)
    private String generatorType;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "width", nullable = false)
    private int width;

    @Column(name = "height", nullable = false)
    private int height;

    @Column(name = "iterations", nullable = false)
    private int iterations;

    @Column(name = "symmetry", nullable = false)
    private int symmetry;

    @Column(name = "gamma", nullable = false)
    private double gamma;

    @Column(name = "transformation_type", nullable = false)
    private String transformationType;

    @Column(name = "image_type", nullable = false)
    private String imageType;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "error_message")
    private String errorMessage;
}
