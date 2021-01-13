package manufacturing.db;

import java.util.ArrayList;
import java.util.List;
import manufacturing.model.Car;
import manufacturing.model.Driver;
import manufacturing.model.Manufacturer;

public class Storage {
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    public static final List<Car> cars = new ArrayList<>();
    public static final List<Driver> drivers = new ArrayList<>();
    private static Long manufacturerId = 0L;
    private static Long carId = 0L;
    private static Long driverId = 0L;

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(++manufacturerId);
        manufacturers.add(manufacturer);
    }

    public static void addCar(Car car) {
        car.setId(++carId);
        cars.add(car);
    }

    public static void addDriver(Driver driver) {
        driver.setId(++driverId);
        drivers.add(driver);
    }
}
