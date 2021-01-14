package manufacturing.service;

import java.util.List;
import java.util.Optional;
import manufacturing.model.Manufacturer;

public interface ManufacturerService {
    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> getById(Long manufacturerId);

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long manufacturerId);

    List<Manufacturer> getAllManufacturers();
}
