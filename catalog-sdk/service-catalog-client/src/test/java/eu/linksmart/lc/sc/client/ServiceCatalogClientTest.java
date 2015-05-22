package eu.linksmart.lc.sc.client;

import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ServiceCatalogClientTest {
	
	//private String BASE_URL = "http://gando.fit.fraunhofer.de:8090/sc";

	private String BASE_URL = "http://localhost:8082/sc";
	//private String BASE_URL = "http://192.168.56.101:8082/sc";
	
	@Test
	public void testCatalogClient() {
		
		String SERVICE_ID = "testserver/broker";
		String SERVICE_NAME = "MqttBroker";
		
		String serviceJson = readFileContents("/registration.json");
		assertTrue(ServiceCatalogClient.getInstance(BASE_URL).add(serviceJson));
		
		String result_gc = ServiceCatalogClient.getInstance(BASE_URL).get(SERVICE_ID);
		assertNotNull(result_gc);
		System.out.println("get-service: " + result_gc);
		
		String updatedServiceJson = readFileContents("/update-registration.json");
		
		assertTrue(ServiceCatalogClient.getInstance(BASE_URL).update(SERVICE_ID, updatedServiceJson));
		
		String result_updated_gs = ServiceCatalogClient.getInstance(BASE_URL).get(SERVICE_ID);
		assertNotNull(result_updated_gs);
		System.out.println("get-updated_service: " + result_updated_gs);
		
		assertTrue(ServiceCatalogClient.getInstance(BASE_URL).delete(SERVICE_ID));
		
		//
		// now add new device registration to test the filtering API
		//
		assertTrue(ServiceCatalogClient.getInstance(BASE_URL).add(readFileContents("/registration.json")));
		
		String result_gs = ServiceCatalogClient.getInstance(BASE_URL).getServices(1, 100);
		assertNotNull(result_gs);
		System.out.println("get-services: " + result_gs);
		
		String result_fs = ServiceCatalogClient.getInstance(BASE_URL).findService("name", "equals", SERVICE_NAME);
		assertNotNull(result_fs);
		System.out.println("find-service: " + result_fs);
		
		String result_fservices = ServiceCatalogClient.getInstance(BASE_URL).findServices("name", "equals", SERVICE_NAME, 1, 100);
		assertNotNull(result_fservices);
		System.out.println("find-services: " + result_fservices);
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
