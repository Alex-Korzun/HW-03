package manufacturing.controllers.cars;

import manufacturing.lib.Injector;
import manufacturing.model.Car;
import manufacturing.service.CarService;
import manufacturing.service.DriverService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("manufacturing");
    private final CarService carService =
            (CarService) injector.getInstance(CarService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String carId = req.getParameter("id");
        Long id = Long.parseLong(carId);
        carService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/cars/");
    }
}
