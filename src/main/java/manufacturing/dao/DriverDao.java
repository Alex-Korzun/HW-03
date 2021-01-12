package manufacturing.dao;

import manufacturing.model.Driver;
import java.util.List;
import java.util.Optional;

public interface DriverDao {
    Driver create(Driver driver);

    Optional<Driver> getById(Long driverId);

    Driver update(Driver driver);

    boolean deleteById(Long driverId);

    boolean delete(Driver driver);

    List<Driver> getAllDrivers();
}
