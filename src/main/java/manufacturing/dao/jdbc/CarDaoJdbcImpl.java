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
                + "(car_model, manufacturer_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create car "
                    + car, e);
        }
        return car;
    }

    @Override
    public Optional<Car> getById(Long carId) {
        String query = "SELECT c.car_id, c.car_model, c.manufacturer_id, "
                + "m.manufacturer_name, m.manufacturer_country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m "
                + "ON m.manufacturer_id = c.manufacturer_id "
                + "WHERE car_id = ? AND c.deleted = FALSE";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Driver> drivers = createDrivers(carId, connection);
            while (resultSet.next()) {
                car = createCar(resultSet, connection);
                car.setDrivers(drivers);
            }
            return Optional.ofNullable(car);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get Car by id "
                    + carId, e);
        }
    }

    @Override
    public Car update(Car car) {
        String deletionQuery = "DELETE FROM cars_drivers WHERE car_id = ?";
        String updateQuery = "UPDATE cars "
                + "SET car_model = ?, manufacturer_id = ? "
                + "WHERE car_id = ? AND deleted = FALSE";
        String insertQuery = "INSERT INTO cars_drivers "
                + "(car_id, driver_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatementForDeletion =
                        connection.prepareStatement(deletionQuery);
                PreparedStatement preparedStatementForUpdating =
                        connection.prepareStatement(updateQuery);
                PreparedStatement preparedStatementForInserting =
                        connection.prepareStatement(insertQuery)) {
            preparedStatementForDeletion.setLong(1, car.getId());
            preparedStatementForDeletion.executeUpdate();
            preparedStatementForUpdating.setString(1, car.getModel());
            preparedStatementForUpdating.setLong(2, car.getManufacturer().getId());
            preparedStatementForUpdating.setLong(3, car.getId());
            preparedStatementForUpdating.executeUpdate();
            preparedStatementForInserting.setLong(1, car.getId());
            List<Driver> drivers = car.getDrivers();
            for (Driver driver : drivers) {
                preparedStatementForInserting.setLong(2, driver.getId());
                preparedStatementForInserting.executeUpdate();
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car in DB", e);
        }
    }

    @Override
    public boolean delete(Long carId) {
        String query = "UPDATE cars "
                + "SET deleted = TRUE "
                + "WHERE car_id = ?";
        int deletedID;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, carId);
            deletedID = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete drivers by id " + carId, e);
        }
        return deletedID > 0;
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT c.car_id, c.car_model, "
                + "c.manufacturer_id, m.manufacturer_name, m.manufacturer_country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m "
                + "ON c.manufacturer_id = m.manufacturer_id "
                + "WHERE c.deleted = FALSE";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(createCar(resultSet, connection));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String query = "SELECT c.car_id "
                + "FROM cars c "
                + "INNER JOIN cars_drivers cd "
                + "ON c.car_id = cd.car_id "
                + "WHERE driver_id = ? AND deleted = FALSE";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(createCar(resultSet, connection));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get Cars by Driver", e);
        }
    }

    private List<Driver> createDrivers(Long carId, Connection connection) {
        String query = "SELECT cd.driver_id, d.driver_name, d.license_number "
                + "FROM cars_drivers cd "
                + "INNER JOIN drivers d "
                + "ON d.driver_id = cd.driver_id "
                + "WHERE cd.car_id = ? AND d.deleted = FALSE";
        List<Driver> drivers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
            String name = resultSet.getString("driver_name");
            String licenseNumber = resultSet.getString("license_number");
            Driver driver = new Driver(name, licenseNumber);
            driver.setId(driverId);
            return driver;
        } catch (Exception e) {
            throw new DataProcessingException("Can't parse driver from result set", e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) {
        try {
            Long manufacturerID = resultSet.getObject("manufacturer_id", Long.class);
            String name = resultSet.getString("manufacturer_name");
            String country = resultSet.getString("manufacturer_country");
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturer.setId(manufacturerID);
            return manufacturer;
        } catch (Exception e) {
            throw new DataProcessingException("Can't parse manufacturer from result set", e);
        }
    }

    private Car createCar(ResultSet resultSet, Connection connection) {
        try {
            String model = resultSet.getString("car_model");
            Long carId = resultSet.getObject("car_id", Long.class);
            Manufacturer manufacturer = createManufacturer(resultSet);
            Car car = new Car(model, manufacturer);
            car.setId(carId);
            car.setDrivers(createDrivers(carId, connection));
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse car from result set ", e);
        }
    }
}
