package manufacturing.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import manufacturing.dao.CarDao;
import manufacturing.exception.DataProcessingException;
import manufacturing.lib.Dao;
import manufacturing.model.Car;
import manufacturing.model.Driver;
import manufacturing.model.Manufacturer;
import manufacturing.util.ConnectionUtil;

@Dao
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String query = "INSERT INTO cars "
                + "(model, manufacturer_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject("GENERATED_KEY", Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create car "
                    + car, e);
        }
        return car;
    }

    @Override
    public Optional<Car> get(Long carId) {
        String query = "SELECT c.id, c.model, c.manufacturer_id, "
                + "m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m "
                + "ON m.id = c.manufacturer_id "
                + "WHERE c.id = ? AND c.deleted = FALSE";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = createCar(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get Car by id "
                    + carId, e);
        }
        if (car != null) {
            car.setDrivers(getAllDriversByCarId(carId));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public Car update(Car car) {
        String updateQuery = "UPDATE cars "
                + "SET model = ?, manufacturer_id = ? "
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatementForUpdating =
                        connection.prepareStatement(updateQuery)) {
            preparedStatementForUpdating.setString(1, car.getModel());
            preparedStatementForUpdating.setLong(2, car.getManufacturer().getId());
            preparedStatementForUpdating.setLong(3, car.getId());
            preparedStatementForUpdating.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car in DB", e);
        }
        removeDriversFromCar(car);
        addDriversToCar(car);
        return car;
    }

    @Override
    public boolean delete(Long carId) {
        String query = "UPDATE cars SET deleted = TRUE WHERE id = ?";
        int deletedRows;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, carId);
            deletedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete drivers by id " + carId, e);
        }
        return deletedRows > 0;
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT c.id, c.model, "
                + "c.manufacturer_id, m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m "
                + "ON c.manufacturer_id = m.id "
                + "WHERE c.deleted = FALSE";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(createCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
        for (Car car: cars) {
            car.setDrivers(getAllDriversByCarId(car.getId()));
        }
        return cars;
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String query = "SELECT c.id, c.model, "
                + "m.id, m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN cars_drivers cd "
                + "ON c.id = cd.car_id "
                + "INNER JOIN manufacturers m "
                + "ON m.id = c.manufacturer_id "
                + "INNER JOIN drivers d "
                + "ON d.id = cd.driver_id "
                + "WHERE d.id = ? AND c.deleted = FALSE "
                + "AND d.deleted = FALSE";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(createCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get Cars by Driver", e);
        }
        for (Car car: cars) {
            car.setDrivers(getAllDriversByCarId(car.getId()));
        }
        return cars;
    }

    private List<Driver> getAllDriversByCarId(Long carId) {
        String query = "SELECT cd.driver_id, d.name, d.license_number, d.login "
                + "FROM cars_drivers cd "
                + "INNER JOIN drivers d "
                + "ON d.id = cd.driver_id "
                + "WHERE cd.car_id = ? AND d.deleted = FALSE";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                drivers.add(createDriver(resultSet));
            }
            return drivers;
        } catch (Exception e) {
            throw new DataProcessingException("Can't parse drivers from car with id "
                    + carId, e);
        }
    }

    private Driver createDriver(ResultSet resultSet) {
        try {
            Long driverId = resultSet.getObject("driver_id", Long.class);
            String name = resultSet.getString("name");
            String licenseNumber = resultSet.getString("license_number");
            String login = resultSet.getString("login");
            Driver driver = new Driver(name, licenseNumber);
            driver.setId(driverId);
            driver.setLogin(login);
            return driver;
        } catch (Exception e) {
            throw new DataProcessingException("Can't parse driver from result set", e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) {
        try {
            Long manufacturerID = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturer.setId(manufacturerID);
            return manufacturer;
        } catch (Exception e) {
            throw new DataProcessingException("Can't parse manufacturer from result set", e);
        }
    }

    private Car createCar(ResultSet resultSet) {
        try {
            String model = resultSet.getString("model");
            Long carId = resultSet.getObject("id", Long.class);
            Manufacturer manufacturer = createManufacturer(resultSet);
            Car car = new Car(model, manufacturer);
            car.setId(carId);
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse car from result set ", e);
        }
    }

    private void removeDriversFromCar(Car car) {
        String query = "DELETE FROM cars_drivers WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't remove Driver from Car", e);
        }
    }

    private void addDriversToCar(Car car) {
        String query = "INSERT INTO cars_drivers (car_id, driver_id) "
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, car.getId());
            for (Driver driver : car.getDrivers()) {
                preparedStatement.setLong(2, driver.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert Driver to Car", e);
        }
    }
}
