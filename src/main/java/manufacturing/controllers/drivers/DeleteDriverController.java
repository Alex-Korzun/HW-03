package manufacturing.controllers.drivers;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import manufacturing.lib.Injector;
import manufacturing.service.DriverService;

public class DeleteDriverController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("manufacturing");
    private final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String driverId = req.getParameter("id");
        Long id = Long.parseLong(driverId);
        driverService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/drivers/");
    }
}
