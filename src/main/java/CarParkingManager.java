import core.CarOperations;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class CarParkingManager {
    static Logger log = Logger.getLogger(CarParkingManager.class.getName());

    public static void main(String[] args){
        try {
            InputStream input = CarParkingManager.class.getClassLoader().getResourceAsStream("config.properties");
            Properties prop = new Properties();
            if (input == null) {
                log.info("Sorry, unable to find config.properties");
                return;
            }
            prop.load(input);
            StringBuilder calledClass = new StringBuilder(prop.getProperty("DBClient"));
            CarOperations operation=new CarOperations();
            operation.initiate(calledClass);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
