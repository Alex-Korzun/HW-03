package manufacturing.controller.driver;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import manufacturing.lib.Injector;
import manufacturing.model.Car;
import manufacturing.model.Driver;
import manufacturing.service.CarService;
import manufacturing.service.DriverService;

public class AddDriverToCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("manufacturing");
    private final CarService carService =
            (CarService) injector.getInstance(CarService.class);
    private final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cars/drivers/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long carId = Long.valueOf(req.getParameter("carId"));
        Long driverId = Long.valueOf(req.getParameter("driverId"));
        Car car = carService.get(carId);
        Driver driver = driverService.get(driverId);
        carService.addDriverToCar(driver, car);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
