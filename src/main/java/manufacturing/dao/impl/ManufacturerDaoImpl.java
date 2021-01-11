package manufacturing.dao.impl;

import manufacturing.dao.ManufacturerDao;
import manufacturing.db.Storage;
import manufacturing.model.Manufacturer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> getById(Long manufacturerId) {
        return Optional.ofNullable(Storage.manufacturers.get(Math.toIntExact(manufacturerId)));
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Manufacturer manufacturerForUpdate = Storage.manufacturers
                .stream()
                .filter(m -> Objects.equals(m.getId(), manufacturer.getId()))
                .findFirst()
                .get();
        manufacturerForUpdate.setName(manufacturer.getName());
        manufacturerForUpdate.setCountry(manufacturer.getCountry());
        return manufacturerForUpdate;
    }

    @Override
    public boolean deleteById(Long manufacturerId) {
        for (Manufacturer manufacturerForDeletion : getAllManufacturers()) {
            if (Objects.equals(manufacturerForDeletion.getId(), manufacturerId)) {
                return Storage.manufacturers.remove(manufacturerForDeletion);
            }
        }
        return false;
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
