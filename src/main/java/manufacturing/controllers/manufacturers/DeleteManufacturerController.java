package manufacturing.controllers.manufacturers;

import manufacturing.lib.Injector;
import manufacturing.model.Manufacturer;
import manufacturing.service.ManufacturerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("manufacturing");
    private final ManufacturerService manufacturerService =
            (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String manufacturerId = req.getParameter("id");
        Long id = Long.parseLong(manufacturerId);
        manufacturerService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/manufacturers/");
    }
}
