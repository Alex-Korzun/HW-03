package manufacturing.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import manufacturing.dao.DriverDao;
import manufacturing.db.Storage;
import manufacturing.lib.Dao;
import manufacturing.model.Driver;

public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return driver;
    }

    @Override
    public Optional<Driver> getById(Long driverId) {
        return Storage.drivers
                .stream()
                .filter(d -> d.getId().equals(driverId))
                .findFirst();
    }

    @Override
    public Driver update(Driver driver) {
        IntStream.range(0, Storage.drivers.size())
                .filter(i -> Storage.drivers.get(i).getId().equals(driver.getId()))
                .forEach(i -> Storage.drivers.set(i, driver));
        return driver;
    }

    @Override
    public boolean delete(Long driverId) {
        return Storage.cars.removeIf(m -> m.getId().equals(driverId));
    }

    @Override
    public List<Driver> getAll() {
        return Storage.drivers;
    }
}
