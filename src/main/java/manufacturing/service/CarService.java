package manufacturing.service;

import java.util.List;
import manufacturing.model.Car;
import manufacturing.model.Driver;

public interface CarService extends GenericService<Car, Long> {
    void addDriverToCar(Driver driver, Car car);

    void removeDriverFromCar(Driver driver, Car car);

    List<Car> getAllByDriver(Long driverId);
}
