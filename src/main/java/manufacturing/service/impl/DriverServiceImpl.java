package manufacturing.service.impl;

import java.util.List;
import manufacturing.dao.DriverDao;
import manufacturing.lib.Inject;
import manufacturing.lib.Service;
import manufacturing.model.Driver;
import manufacturing.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService {
    @Inject
    private DriverDao driverDao;

    @Override
    public Driver create(Driver driver) {
        return driverDao.create(driver);
    }

    @Override
    public Driver get(Long id) {
        return driverDao.getById(id).orElseThrow();
    }

    @Override
    public List<Driver> getAll() {
        return driverDao.getAllDrivers();
    }

    @Override
    public Driver update(Driver driver) {
        return driverDao.update(driver);
    }

    @Override
    public boolean delete(Long id) {
        return driverDao.deleteById(id);
    }
}
