package manufacturing.service.impl;

import manufacturing.dao.CarDao;
import manufacturing.dao.DriverDao;
import manufacturing.lib.Inject;
import manufacturing.lib.Service;
import manufacturing.model.Car;
import manufacturing.model.Driver;
import manufacturing.service.CarService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;
    @Inject
    private DriverDao driverDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        return carDao.getById(id).orElseThrow();
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAllCars();
    }

    @Override
    public Car update(Car car) {
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.deleteById(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        driverDao.create(driver);
        car.getDrivers().add(driver);
        carDao.update(car);
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        car.getDrivers().remove(driver);
        carDao.update(car);
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return carDao.getAllCars().stream()
                .filter(c -> c.getDrivers()
                        .stream()
                        .map(Driver::getId)
                        .collect(Collectors.toList())
                        .contains(driverId))
                .collect(Collectors.toList());
    }
}
