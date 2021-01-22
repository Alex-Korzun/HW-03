package manufacturing.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import manufacturing.dao.DriverDao;
import manufacturing.exception.DataProcessingException;
import manufacturing.lib.Dao;
import manufacturing.model.Driver;
import manufacturing.util.ConnectionUtil;

@Dao
public class DriverDaoJdbcImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String query = "INSERT INTO drivers "
                + "(name, license_number, login, password) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenseNumber());
            preparedStatement.setString(3, driver.getLogin());
            preparedStatement.setString(4, driver.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject("GENERATED_KEY", Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create driver "
                    + driver, e);
        }
        return driver;
    }

    @Override
    public Optional<Driver> get(Long driverId) {
        String query = "SELECT * FROM drivers "
                + "WHERE id = ? AND deleted = FALSE";
        Driver driver = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                driver = createDriver(resultSet);
            }
            return Optional.ofNullable(driver);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get Driver by id "
                    + driverId, e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        String query = "UPDATE drivers "
                + "SET name = ?, license_number = ?, "
                + "login = ?, password = ? "
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenseNumber());
            preparedStatement.setString(3, driver.getLogin());
            preparedStatement.setString(4, driver.getPassword());
            preparedStatement.setLong(5, driver.getId());
            preparedStatement.executeUpdate();
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update driver in DB", e);
        }
    }

    @Override
    public boolean delete(Long driverId) {
        String query = "UPDATE drivers "
                + "SET deleted = TRUE "
                + "WHERE id = ?";
        int deletedRows;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, driverId);
            deletedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete drivers by id "
                    + driverId, e);
        }
        return deletedRows > 0;
    }

    @Override
    public List<Driver> getAll() {
        String query = "SELECT * FROM drivers "
                + "WHERE deleted = FALSE";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                drivers.add(createDriver(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all drivers", e);
        }
    }

    private Driver createDriver(ResultSet resultSet) {
        try {
            String name = resultSet.getString("name");
            String licenseNumber = resultSet.getString("license_number");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            Long driverId = resultSet.getObject("id", Long.class);
            Driver driver = new Driver(name, licenseNumber);
            driver.setId(driverId);
            driver.setLogin(login);
            driver.setPassword(password);
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't parse driver from result set ", e);
        }
    }

    @Override
    public Optional<Driver> findByLogin(String login) {
        String query = "SELECT * FROM drivers "
                + "WHERE login = ? AND deleted = FALSE";
        Driver driver = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                driver = createDriver(resultSet);
            }
            return Optional.ofNullable(driver);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find Driver by this login", e);
        }
    }
}
