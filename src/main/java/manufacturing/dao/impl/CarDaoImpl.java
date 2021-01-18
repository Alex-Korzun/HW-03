package manufacturing.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import manufacturing.dao.CarDao;
import manufacturing.db.Storage;
import manufacturing.lib.Dao;
import manufacturing.model.Car;

public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Optional<Car> getById(Long carId) {
        return Storage.cars
                .stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst();
    }

    @Override
    public Car update(Car car) {
        IntStream.range(0, Storage.cars.size())
                .filter(i -> Storage.cars.get(i).getId().equals(car.getId()))
                .forEach(i -> Storage.cars.set(i, car));
        return car;
    }

    @Override
    public boolean delete(Long carId) {
        return Storage.cars.removeIf(m -> m.getId().equals(carId));
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return Storage.cars.stream()
                .filter(c -> c.getDrivers()
                        .stream()
                        .anyMatch(d -> d.getId().equals(driverId)))
                .collect(Collectors.toList());
    }
}
