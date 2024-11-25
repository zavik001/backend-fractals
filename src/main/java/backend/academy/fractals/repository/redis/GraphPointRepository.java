package backend.academy.fractals.repository.redis;

import backend.academy.fractals.entity.GraphPointEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GraphPointRepository extends CrudRepository<GraphPointEntity, String> {
    List<GraphPointEntity> findByGeneratorType(String generatorType);
}
