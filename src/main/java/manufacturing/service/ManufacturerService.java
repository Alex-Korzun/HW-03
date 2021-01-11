package manufacturing.service;

import manufacturing.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {
    Manufacturer create(Manufacturer manufacturer);

    Optional<Manufacturer> getByTd(Long manufacturerId);

    Manufacturer update(Manufacturer manufacturer);

    boolean deleteById(Long manufacturerId);

    boolean delete(Manufacturer manufacturer);

    List<Manufacturer> getAllManufacturers();
}
