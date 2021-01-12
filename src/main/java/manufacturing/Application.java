package manufacturing;

import manufacturing.lib.Injector;
import manufacturing.model.Car;
import manufacturing.model.Driver;
import manufacturing.model.Manufacturer;
import manufacturing.service.CarService;
import manufacturing.service.DriverService;
import manufacturing.service.ManufacturerService;

public class Application {
    private static final Injector injector = Injector.getInstance("manufacturing");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService) injector
                .getInstance(ManufacturerService.class);
        Manufacturer firstManufacturer = new Manufacturer("Audi", "Germany");
        Manufacturer secondManufacturer = new Manufacturer("Ferrari", "Italy");
        Manufacturer thirdManufacturer = new Manufacturer("Cadillac", "USA");
        Manufacturer fourthManufacturer = new Manufacturer("Toyota", "Japan");

        manufacturerService.create(firstManufacturer);
        manufacturerService.create(secondManufacturer);
        manufacturerService.create(thirdManufacturer);
        manufacturerService.create(fourthManufacturer);
        System.out.println(manufacturerService.getAllManufacturers().toString());

        Manufacturer updatedManufacturer = new Manufacturer("BMW", "Germany");
        updatedManufacturer.setId(1L);
        manufacturerService.update(updatedManufacturer);
        System.out.println(manufacturerService.getAllManufacturers().toString());

        manufacturerService.deleteById(1L);
        System.out.println(manufacturerService.getAllManufacturers().toString());

        manufacturerService.delete(secondManufacturer);
        System.out.println(manufacturerService.getAllManufacturers().toString());


        DriverService driverService = (DriverService) injector
                .getInstance(DriverService.class);
        Driver firstDriver = new Driver("Bob", "AB123CD");
        Driver secondDriver = new Driver("Alice", "AS546BN");
        Driver thirdDriver = new Driver("John", "RE654MT");
        Driver fourthDriver = new Driver("Keanu", "BAD123ASS");
        driverService.create(firstDriver);
        driverService.create(secondDriver);
        driverService.create(thirdDriver);
        driverService.create(fourthDriver);
        System.out.println();
        System.out.println(driverService.getAll().toString());

        Driver updatedDriver = driverService.get(2L);
        updatedDriver.setName("James");
        updatedDriver.setLicenceNumber("DF123OK");
        driverService.update(updatedDriver);
        System.out.println(driverService.getAll().toString());

        driverService.delete(1L);
        System.out.println(driverService.getAll().toString());


        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car firstCar = new Car("RS 6", firstManufacturer);
        Car secondCar = new Car("LaFerrari", secondManufacturer);
        Car thirdCar = new Car("XTS", thirdManufacturer);
        Car fourthCar = new Car("Camry", fourthManufacturer);
        carService.create(firstCar);
        carService.create(secondCar);
        carService.create(thirdCar);
        carService.create(fourthCar);
        System.out.println();
        System.out.println(carService.getAll().toString());

        carService.addDriverToCar(firstDriver, firstCar);
        carService.addDriverToCar(secondDriver, secondCar);
        carService.addDriverToCar(thirdDriver, thirdCar);
        carService.addDriverToCar(fourthDriver, fourthCar);
        carService.addDriverToCar(fourthDriver, secondCar);
        System.out.println(carService.getAll().toString());

        Car updatedCar = carService.get(0L);
        updatedCar.setModel("Q8");
        carService.update(updatedCar);
        System.out.println(carService.get(0L).toString());

        carService.removeDriverFromCar(firstDriver, firstCar);
        System.out.println(carService.getAll().toString());
    }
}
