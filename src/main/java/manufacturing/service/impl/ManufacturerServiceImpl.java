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
    private ManufacturerDao manufacturerDao;

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
    public boolean delete(Long manufacturerId) {
        return manufacturerDao.delete(manufacturerId);
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerDao.getAllManufacturers();
    }
}
