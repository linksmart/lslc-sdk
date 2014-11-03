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
		
		String DEVICE_ID = "testdc/device1";
		String RESOURCE_ID = "testdc/device1/resource1";
		String UPDATED_RESOURCE_ID = "testdc/device1/resource2";
		String DEVICE_NAME = "device1";
		String RESOURCE_NAME = "resource1";
		
		String deviceJson = readFileContents("/registration.json");
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).add(deviceJson));
		
		String result_gd = ResourceCatalogClient.getInstance(BASE_URL).get(DEVICE_ID);
		assertNotNull(result_gd);
		System.out.println("get-device: " + result_gd);
		
		String result_gr = ResourceCatalogClient.getInstance(BASE_URL).getResource(RESOURCE_ID);
		assertNotNull(result_gr);
		System.out.println("get-resource: " + result_gr);
		
		String updatedDeviceJson = readFileContents("/update-registration.json");
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).update(DEVICE_ID, updatedDeviceJson));
		
		String result_updated_gd = ResourceCatalogClient.getInstance(BASE_URL).get(DEVICE_ID);
		assertNotNull(result_updated_gd);
		System.out.println("get-updated_device: " + result_updated_gd);
		
		String result_updated_gr = ResourceCatalogClient.getInstance(BASE_URL).getResource(UPDATED_RESOURCE_ID);
		assertNotNull(result_updated_gd);
		System.out.println("get-updated_resource: " + result_updated_gr);
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).delete(DEVICE_ID));
		
		//
		// now add new device registration to test the filtering API
		//
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).add(readFileContents("/registration.json")));
		
		String result_gdev = ResourceCatalogClient.getInstance(BASE_URL).getDevices(1, 10);
		assertNotNull(result_gdev);
		System.out.println("get-devices: " + result_gdev);
		
		String result_fd = ResourceCatalogClient.getInstance(BASE_URL).findDevice("name", "equals", DEVICE_NAME);
		assertNotNull(result_fd);
		System.out.println("find-device: " + result_fd);
		
		String result_fds = ResourceCatalogClient.getInstance(BASE_URL).findDevices("name", "equals", DEVICE_NAME, 1, 100);
		assertNotNull(result_fds);
		System.out.println("find-devices: " + result_fds);
		
		String result_fr = ResourceCatalogClient.getInstance(BASE_URL).findResource("name", "equals", RESOURCE_NAME);
		assertNotNull(result_fr);
		System.out.println("find-resource: " + result_fr);
		
		String result_frs = ResourceCatalogClient.getInstance(BASE_URL).findResources("name", "equals", RESOURCE_NAME, 1, 100);
		assertNotNull(result_frs);
		System.out.println("find-resources: " + result_frs);
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).delete(DEVICE_ID));
		
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
