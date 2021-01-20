package manufacturing.controllers.drivers;

import manufacturing.lib.Injector;
import manufacturing.service.DriverService;
import manufacturing.service.ManufacturerService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
