package manufacturing.service.impl;

import java.util.List;
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
    public Manufacturer get(Long manufacturerId) {
        return manufacturerDao.get(manufacturerId).get();
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
    public List<Manufacturer> getAll() {
        return manufacturerDao.getAll();
    }
}
