package manufacturing.dao;

import java.util.Optional;
import manufacturing.model.Driver;

public interface DriverDao extends GenericDao<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
