package eu.linksmart.lc.rc.client;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

import eu.linksmart.lc.rc.client.ResourceCatalogClient;

public class ResourceCatalogClientTest {
	
	private String BASE_URL = "http://gando.fit.fraunhofer.de:8091/rc";
	
	@Test
	public void testCatalogClient() {
		
		String deviceJson = readFileContents("/registration.json");
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).registerDevice(deviceJson));
		
		String result_gd = ResourceCatalogClient.getInstance(BASE_URL).getDevice("testdc/device1");
		assertNotNull(result_gd);
		System.out.println("get-device: " + result_gd);
		
		String result_gr = ResourceCatalogClient.getInstance(BASE_URL).getResource("testdc/device1/resource1");
		assertNotNull(result_gr);
		System.out.println("get-resource: " + result_gr);
		
		String result_sd = ResourceCatalogClient.getInstance(BASE_URL).search("device", "name", "equals", "device1");
		assertNotNull(result_sd);
		System.out.println("search-device: " + result_sd);
		
		String updatedDeviceJson = readFileContents("/update-registration.json");
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).updateDevice("testdc/device1", updatedDeviceJson));
		
		String result_updated_gd = ResourceCatalogClient.getInstance(BASE_URL).getDevice("testdc/device1");
		assertNotNull(result_updated_gd);
		System.out.println("get-updated_device: " + result_updated_gd);
		
		String result_updated_gr = ResourceCatalogClient.getInstance(BASE_URL).getResource("testdc/device1/resource2");
		assertNotNull(result_updated_gd);
		System.out.println("get-updated_resource: " + result_updated_gr);
		
		String result_updated_sr = ResourceCatalogClient.getInstance(BASE_URL).search("resource", "name", "equals", "resource2");
		assertNotNull(result_updated_sr);
		System.out.println("search-updated_resource: " + result_updated_sr);
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).deleteDevice("testdc/device1"));
		
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
