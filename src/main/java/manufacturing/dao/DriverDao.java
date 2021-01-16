package manufacturing.dao;

import java.util.List;
import java.util.Optional;
import manufacturing.model.Driver;

public interface DriverDao {
    Driver create(Driver driver);

    Optional<Driver> getById(Long driverId);

    Driver update(Driver driver);

    boolean delete(Long driverId);

    List<Driver> getAll();
}
