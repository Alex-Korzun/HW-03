package manufacturing.dao.impl;

import manufacturing.dao.CarDao;
import manufacturing.db.Storage;
import manufacturing.lib.Dao;
import manufacturing.model.Car;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Optional<Car> getById(Long carId) {
        return Optional.ofNullable(Storage.cars.get(carId.intValue()));
    }

    @Override
    public Car update(Car car) {
        IntStream.range(0, Storage.cars.size())
                .filter(i -> Storage.cars.get(i).getId().equals(car.getId()))
                .forEach(i -> Storage.cars.set(i, car));
        return car;
    }

    @Override
    public boolean deleteById(Long carId) {
        return Storage.cars.removeIf(m -> m.getId().equals(carId));
    }

    @Override
    public boolean delete(Car car) {
        return Storage.cars.remove(car);
    }

    @Override
    public List<Car> getAllCars() {
        return Storage.cars;
    }
}
