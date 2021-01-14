package manufacturing.dao.jdbc;

import manufacturing.dao.ManufacturerDao;
import manufacturing.exception.DataProcessingException;
import manufacturing.lib.Dao;
import manufacturing.model.Manufacturer;
import manufacturing.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Dao
public class ManufacturerDaoJdbcImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers "
                + "(manufacturer_name, manufacturer_country) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject("manufacturer_id", Long.class));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer ", e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> getById(Long manufacturerId) {
        String query = "SELECT * FROM manufacturers " +
                "WHERE manufacturer_id = ? AND `delete` = FALSE";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, manufacturerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                manufacturer = createManufacturer(resultSet);
            }
            preparedStatement.close();
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id "
                    + manufacturerId, e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers "
                + "SET manufacturer_name = ?, manufacturer_country = ?"
                + "WHERE manufacturer_id = ? AND `delete` = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer in BD", e);
        }
    }

    @Override
    public boolean delete(Long manufacturerId) {
        String query = "UPDATE manufacturers " +
                "SET `delete` = TRUE " +
                "WHERE manufacturer_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, manufacturerId);
            preparedStatement.close();
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by following id "
                    + manufacturerId, e);
        }
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        String query = "SELECT * FROM manufacturers "
                + "WHERE `delete` = FALSE";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(createManufacturer(resultSet));
            }
            preparedStatement.close();
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers!", e);
        }
    }

    private Manufacturer createManufacturer(ResultSet resultSet) {
        try {
            String name = resultSet.getString("manufacturer_name");
            String country = resultSet.getString("manufacturer_country");
            Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturer.setId(manufacturerId);
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer", e);
        }

    }
}