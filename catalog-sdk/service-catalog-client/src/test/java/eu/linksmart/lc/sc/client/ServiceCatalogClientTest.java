package eu.linksmart.lc.sc.client;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

public class ServiceCatalogClientTest {
	
	private String BASE_URL = "http://gando.fit.fraunhofer.de:8090/sc";
	
	@Test
	public void testCatalogClient() {
		
		String serviceJson = readFileContents("/registration.json");
		assertTrue(ServiceCatalogClient.getInstance(BASE_URL).registerService(serviceJson));
		
		String result_gc = ServiceCatalogClient.getInstance(BASE_URL).getService("testserver/broker");
		assertNotNull(result_gc);
		System.out.println("get-service: " + result_gc);
		
		String result_sd = ServiceCatalogClient.getInstance(BASE_URL).search("service", "name", "equals", "MqttBroker");
		assertNotNull(result_sd);
		System.out.println("search-service: " + result_sd);
		
		String updatedServiceJson = readFileContents("/update-registration.json");
		
		assertTrue(ServiceCatalogClient.getInstance(BASE_URL).updateService("testserver/broker", updatedServiceJson));
		
		String result_updated_gs = ServiceCatalogClient.getInstance(BASE_URL).getService("testserver/broker");
		assertNotNull(result_updated_gs);
		System.out.println("get-updated_service: " + result_updated_gs);
		
		assertTrue(ServiceCatalogClient.getInstance(BASE_URL).deleteService("testserver/broker"));
		
	}
	
	private String readFileContents(String fileName) {
		
		File jsonDataFile = new File((this.getClass().getResource((fileName)).getFile()));
		
		StringBuilder fileContents = null;
		Scanner scanner = null;
	
		try {
			fileContents = new StringBuilder((int)jsonDataFile.length());
			scanner = new Scanner(jsonDataFile);
			String lineSeparator = System.getProperty("line.separator");
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	        scanner.close();
	    }
		return fileContents.toString();
	}
	
}
