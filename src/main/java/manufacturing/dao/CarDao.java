package manufacturing.dao;

import java.util.List;
import java.util.Optional;

import manufacturing.lib.Dao;
import manufacturing.model.Car;

@Dao
public interface CarDao {
    Car create(Car car);

    Optional<Car> getById(Long carId);

    Car update(Car car);

    boolean delete(Long carId);

    List<Car> getAll();

    List<Car> getAllByDriver(Long driverId);
}
