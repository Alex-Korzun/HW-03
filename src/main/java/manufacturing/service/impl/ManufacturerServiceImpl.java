package manufacturing.service.impl;

import java.util.List;
import java.util.Optional;
import manufacturing.dao.ManufacturerDao;
import manufacturing.lib.Inject;
import manufacturing.lib.Service;
import manufacturing.model.Manufacturer;
import manufacturing.service.ManufacturerService;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Inject
    ManufacturerDao manufacturerDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return manufacturerDao.create(manufacturer);
    }

    @Override
    public Optional<Manufacturer> getById(Long manufacturerId) {
        return manufacturerDao.getById(manufacturerId);
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return manufacturerDao.update(manufacturer);
    }

    @Override
    public boolean deleteById(Long manufacturerId) {
        return manufacturerDao.deleteById(manufacturerId);
    }

    @Override
    public boolean delete(Manufacturer manufacturer) {
        return manufacturerDao.delete(manufacturer);
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerDao.getAllManufacturers();
    }
}
