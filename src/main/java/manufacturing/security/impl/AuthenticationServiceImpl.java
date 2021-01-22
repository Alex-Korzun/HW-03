package manufacturing.security.impl;

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
        Driver driverFromDB = driverService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException("Incorrect login or password"));
        if (driverFromDB.getPassword().equals(password)) {
            return driverFromDB;
        }
        throw new AuthenticationException("Incorrect login or password");
    }
}
