package manufacturing.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import manufacturing.dao.ManufacturerDao;
import manufacturing.db.Storage;
import manufacturing.lib.Dao;
import manufacturing.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> getById(Long manufacturerId) {
        return Optional.ofNullable(Storage.manufacturers.get(manufacturerId.intValue()));
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        IntStream.range(0, Storage.manufacturers.size())
                .filter(i -> Storage.manufacturers.get(i).getId().equals(manufacturer.getId()))
                .forEach(i -> Storage.manufacturers.set(i, manufacturer));
        return manufacturer;
    }

    @Override
    public boolean deleteById(Long manufacturerId) {
        return Storage.manufacturers.removeIf(m -> m.getId().equals(manufacturerId));
    }

    @Override
    public boolean delete(Manufacturer manufacturer) {
        return Storage.manufacturers.remove(manufacturer);
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return Storage.manufacturers;
    }
}
