package manufacturing.service.impl;

import manufacturing.model.Manufacturer;
import manufacturing.service.ManufacturerService;

import java.util.List;
import java.util.Optional;

public class ManufacturerServiceImpl implements ManufacturerService {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public Optional<Manufacturer> getByTd(Long manufacturerId) {
        return Optional.empty();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean deleteById(Long manufacturerId) {
        return false;
    }

    @Override
    public boolean delete(Manufacturer manufacturer) {
        return false;
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return null;
    }
}
