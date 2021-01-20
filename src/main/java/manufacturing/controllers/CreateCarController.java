package manufacturing.controllers;

import manufacturing.lib.Injector;
import manufacturing.model.Car;
import manufacturing.model.Manufacturer;
import manufacturing.service.CarService;
import manufacturing.service.ManufacturerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("manufacturing");
    private final CarService carService =
            (CarService) injector.getInstance(CarService.class);
    private final ManufacturerService manufacturerService =
            (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cars/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String model = req.getParameter("model");
        String manufacturer = req.getParameter("manufacturer");
        Long manufacturerId = Long.parseLong(manufacturer);
        Car car = new Car(model, manufacturerService.get(manufacturerId));
        carService.create(car);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
