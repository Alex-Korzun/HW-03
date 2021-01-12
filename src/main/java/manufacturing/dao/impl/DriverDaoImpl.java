package manufacturing.dao.impl;

import manufacturing.dao.DriverDao;
import manufacturing.db.Storage;
import manufacturing.lib.Dao;
import manufacturing.model.Driver;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return driver;
    }

    @Override
    public Optional<Driver> getById(Long driverId) {
        return Optional.ofNullable(Storage.drivers.get(driverId.intValue()));
    }

    @Override
    public Driver update(Driver driver) {
        IntStream.range(0, Storage.drivers.size())
                .filter(i -> Storage.drivers.get(i).getId().equals(driver.getId()))
                .forEach(i -> Storage.drivers.set(i, driver));
        return driver;
    }

    @Override
    public boolean deleteById(Long driverId) {
        return Storage.drivers.removeIf(m -> m.getId().equals(driverId));
    }

    @Override
    public boolean delete(Driver driver) {
        return Storage.drivers.remove(driver);
    }

    @Override
    public List<Driver> getAllDrivers() {
        return Storage.drivers;
    }
}
