import core.CarOperation;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;


public class CarParkingManager {
    static Logger log = Logger.getLogger(CarParkingManager.class.getName());

    public static void main(String[] args){
        try {
            InputStream input = CarParkingManager.class.getClassLoader().getResourceAsStream("config");
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            prop.load(input);
            StringBuilder calledClass = new StringBuilder(prop.getProperty("DBClient"));
            CarOperation operation=new CarOperation();
            operation.initiate(calledClass);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}

