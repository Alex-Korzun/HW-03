package manufacturing.dao.impl;

import manufacturing.dao.CarDao;
import manufacturing.lib.Dao;
import manufacturing.model.Car;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        return null;
    }

    @Override
    public Optional<Car> getById(Long carId) {
        return Optional.empty();
    }

    @Override
    public Car update(Car car) {
        return null;
    }

    @Override
    public boolean deleteById(Long carId) {
        return false;
    }

    @Override
    public boolean delete(Car car) {
        return false;
    }

    @Override
    public List<Car> getAllCars() {
        return null;
    }
}
