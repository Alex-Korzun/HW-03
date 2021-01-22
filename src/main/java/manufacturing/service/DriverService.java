package manufacturing.service;

import java.util.Optional;
import manufacturing.model.Driver;

public interface DriverService extends GenericService<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
