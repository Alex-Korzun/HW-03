package manufacturing;

import manufacturing.lib.Injector;
import manufacturing.model.Manufacturer;
import manufacturing.service.ManufacturerService;

public class Application {
    private static final Injector injector = Injector.getInstance("manufacturing");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService) injector
                .getInstance(ManufacturerService.class);
        Manufacturer firstManufacturer = new Manufacturer("Audi", "Germany");
        Manufacturer secondManufacturer = new Manufacturer("Ferrari", "Italy");
        Manufacturer thirdManufacturer = new Manufacturer("Cadillac", "USA");
        manufacturerService.create(firstManufacturer);
        manufacturerService.create(secondManufacturer);
        manufacturerService.create(thirdManufacturer);
        System.out.println(manufacturerService.getAllManufacturers().toString());
        Manufacturer updatedManufacturer = new Manufacturer("BMW", "Germany");
        updatedManufacturer.setId(1L);
        manufacturerService.update(updatedManufacturer);
        System.out.println(manufacturerService.getAllManufacturers().toString());
        manufacturerService.deleteById(1L);
        System.out.println(manufacturerService.getAllManufacturers().toString());
        manufacturerService.delete(secondManufacturer);
        System.out.println(manufacturerService.getAllManufacturers().toString());
    }
}
