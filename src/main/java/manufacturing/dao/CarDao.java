package manufacturing.dao;

import java.util.List;
import java.util.Optional;
import manufacturing.model.Car;

public interface CarDao {
    Car create(Car car);

    Optional<Car> getById(Long carId);

    Car update(Car car);

    boolean deleteById(Long carId);

    boolean delete(Car car);

    List<Car> getAllCars();
}
