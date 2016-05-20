package org.bonitasoft.proxy.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProxyTester {

	public static void main(String[] args) {
		String propertiesPath = args[1];
		Properties conf = new Properties();
		try(FileInputStream fis = new FileInputStream(new File(propertiesPath))){
			conf.load(fis);
		} catch ( IOException e) {
			e.printStackTrace();
		}
	
	}

}
