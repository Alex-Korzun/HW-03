package manufacturing.security.impl;

import java.util.Optional;
import manufacturing.exception.AuthenticationException;
import manufacturing.lib.Inject;
import manufacturing.lib.Service;
import manufacturing.model.Driver;
import manufacturing.security.AuthenticationService;
import manufacturing.service.DriverService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Optional<Driver> driver = driverService.findByLogin(login);
        if (driver.isPresent() && driver.get().getPassword().equals(password)) {
            return driver.get();
        }
        throw new AuthenticationException("Incorrect login or password");
    }
}
