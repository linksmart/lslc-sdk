package eu.linksmart.lc.sc.client;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ServiceCatalogClientTest {
	
	private String BASE_URL = "http://localhost:8082";
	
	//@Test
	public void testCatalogClient() {
				
		String serviceJson = readFileContents("/registration.json");
		
		String serviceUrl = ServiceCatalogClient.getInstance(BASE_URL).add(serviceJson);
		assertNotNull(serviceUrl);
		System.out.println(serviceUrl);
		
		String serviceId = getServiceId(serviceUrl);
		
		String result_gc = ServiceCatalogClient.getInstance(BASE_URL).get(serviceId);
		assertNotNull(result_gc);
		System.out.println("get-service: " + result_gc);
		
		String updatedServiceJson = readFileContents("/update-registration.json");
		
		assertTrue(ServiceCatalogClient.getInstance(BASE_URL).update(serviceId, updatedServiceJson));
		
		String result_updated_gs = ServiceCatalogClient.getInstance(BASE_URL).get(serviceId);
		assertNotNull(result_updated_gs);
		System.out.println("get-updated_service: " + result_updated_gs);
		
		assertTrue(ServiceCatalogClient.getInstance(BASE_URL).delete(serviceId));
		
		//
		// now add new device registration to test the filtering API
		//
		assertNotNull(ServiceCatalogClient.getInstance(BASE_URL).add(readFileContents("/update-registration.json")));
		
		String result_gs = ServiceCatalogClient.getInstance(BASE_URL).getServices(1, 100);
		assertNotNull(result_gs);
		System.out.println("get-services: " + result_gs);
		
		String result_fs = ServiceCatalogClient.getInstance(BASE_URL).findServices("name", "equals", "MqttBroker");
		assertNotNull(result_fs);
		System.out.println("find-service: " + result_fs);
		
		String result_fservices = ServiceCatalogClient.getInstance(BASE_URL).findServices("name", "equals", "MqttBroker", 1, 100);
		assertNotNull(result_fservices);
		System.out.println("find-services: " + result_fservices);
	}
	
	private String readFileContents(String fileName) {
		
		//File jsonDataFile = new File((this.getClass().getResource((fileName)).getFile()));
		
		File jsonDataFile = null;

		try {
			URL aURL =this.getClass().getResource(fileName);
			System.out.println("aURL: " + aURL.toString());
			jsonDataFile = new File(aURL.toURI());
			System.out.println("file: " + jsonDataFile.toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

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
	
	private String getServiceId(String serviceUrl) {
		StringBuilder sb = new StringBuilder(serviceUrl);
        sb.delete(0, (sb.indexOf("/", 1)) + 1);
        return sb.toString();
	}
	
}
