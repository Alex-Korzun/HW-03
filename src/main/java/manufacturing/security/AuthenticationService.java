package manufacturing.security;

import manufacturing.exception.AuthenticationException;
import manufacturing.model.Driver;

public interface AuthenticationService {
    Driver login(String login, String password) throws AuthenticationException;
}
