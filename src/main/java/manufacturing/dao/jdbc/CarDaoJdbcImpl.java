package manufacturing.dao.jdbc;

import manufacturing.dao.CarDao;
import manufacturing.lib.Dao;
import manufacturing.model.Car;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoJdbcImpl implements CarDao {
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
    public boolean delete(Long carId) {
        return false;
    }

    @Override
    public List<Car> getAll() {
        return null;
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return null;
    }
}
