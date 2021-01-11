package manufacturing.db;

import java.util.ArrayList;
import java.util.List;
import manufacturing.model.Manufacturer;

public class Storage {
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    private static Long manufacturerId = 0L;

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturer.setId(++manufacturerId);
        manufacturers.add(manufacturer);
    }
}
