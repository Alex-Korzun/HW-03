package manufacturing.dao;

import java.util.List;
import manufacturing.model.Car;

public interface CarDao extends GenericDao<Car, Long> {
    List<Car> getAllByDriver(Long driverId);
}
