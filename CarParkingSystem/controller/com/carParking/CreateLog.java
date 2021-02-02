package com.carParking;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CreateLog {
	public Logger logger;
	FileHandler fileHandler;
	
	public CreateLog(String file) throws SecurityException, IOException {
		
		File f=new File(file);
		if(!f.exists()){
			f.createNewFile();
		}
		fileHandler=new FileHandler(file,true);
		logger=Logger.getLogger("test");
		logger.addHandler(fileHandler);
		SimpleFormatter formatter=new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		
		
	}
	
}
