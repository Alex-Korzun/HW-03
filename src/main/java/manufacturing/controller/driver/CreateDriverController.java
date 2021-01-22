package manufacturing.controller.driver;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.protocol.x.XMessage;
import manufacturing.lib.Injector;
import manufacturing.model.Driver;
import manufacturing.service.DriverService;

public class CreateDriverController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("manufacturing");
    private final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/driver/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String name = req.getParameter("name");
        String licenseNumber = req.getParameter("licenseNumber");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String submitPassword = req.getParameter("submitPassword");
        Driver driver = new Driver(name, licenseNumber);
        driver.setLogin(login);
        if (password.equals(submitPassword)) {
            driver.setPassword(password);
            driverService.create(driver);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("errorMsg", "Passwords should be equals");
            req.setAttribute("login", "login");
            req.getRequestDispatcher("/WEB-INF/views/driver/create.jsp").forward(req, resp);
        }
    }
}
