package manufacturing.dao;

import manufacturing.model.Manufacturer;
import java.util.List;
import java.util.Optional;

public interface ManufacturerDao {
    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> getById(Long manufacturerId);

    Manufacturer update(Manufacturer manufacturer);

    boolean deleteById(Long manufacturerId);

    boolean delete(Manufacturer manufacturer);

    List<Manufacturer> getAllManufacturers();
}
