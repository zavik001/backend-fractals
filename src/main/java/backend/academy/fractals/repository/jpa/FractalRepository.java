package backend.academy.fractals.repository.jpa;

import backend.academy.fractals.entity.FractalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FractalRepository extends JpaRepository<FractalEntity, Long> {
}
