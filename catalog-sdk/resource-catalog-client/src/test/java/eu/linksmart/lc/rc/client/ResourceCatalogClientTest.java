package eu.linksmart.lc.rc.client;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ResourceCatalogClientTest {
	
	private String BASE_URL = "http://localhost:8081";
	
	//@Test
	public void testCatalogClient() {
		
		String deviceJson = readFileContents("/registration.json");
		
		String registration_url = ResourceCatalogClient.getInstance(BASE_URL).add(deviceJson);
		assertNotNull(registration_url);
		
		System.out.println("registration_url: " + registration_url);
		System.out.println("registration_url_id: " + getId(registration_url));
		
		String result_gd = ResourceCatalogClient.getInstance(BASE_URL).get(registration_url);
		assertNotNull(result_gd);
		System.out.println("get-registration: " + result_gd);
		
		String updatedDeviceJson = readFileContents("/update-registration.json");
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).update(registration_url, updatedDeviceJson));
		
		String result_updated_gd = ResourceCatalogClient.getInstance(BASE_URL).get(registration_url);
		assertNotNull(result_updated_gd);
		System.out.println("get-updated_device: " + result_updated_gd);
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).delete(registration_url));
		
		// with given ID
		String registration_id = "sampleId";
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).add(registration_id, deviceJson));
		
		String id_gd = ResourceCatalogClient.getInstance(BASE_URL).getById(registration_id);
		assertNotNull(id_gd);
		System.out.println("get-id-registration: " + id_gd);
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).updateById(registration_id, updatedDeviceJson));
		
		String id_updated_gd = ResourceCatalogClient.getInstance(BASE_URL).getById(registration_id);
		assertNotNull(id_updated_gd);
		System.out.println("get-id-updated_device: " + id_updated_gd);
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).deleteById(registration_id));
		
		// get/find devices and resources
		
		String device_id = "sample_device_2";
		String resource_id = "resource2";
		
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).add(device_id, updatedDeviceJson));
		
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).getCatalogInfo());
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).getDevices());
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).getDevices(1,100));
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).findDevices("id", "equals", device_id));
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).findDevices("id", "equals", device_id, 1, 100));
		
		// get resources				
		String result_updated_gr = ResourceCatalogClient.getInstance(BASE_URL).getResourceById(resource_id);
		assertNotNull(result_updated_gd);
		System.out.println("get-resource_by_id: " + result_updated_gr);
		
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).getResources());
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).getResources(1,100));
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).findResources("id", "equals", resource_id));
		assertNotNull(ResourceCatalogClient.getInstance(BASE_URL).findResources("id", "equals", resource_id, 1, 100));
		
		assertTrue(ResourceCatalogClient.getInstance(BASE_URL).deleteById(device_id));
		
		System.out.println("testCatalogClient is successful");
		
	}
	
	private String readFileContents(String fileName) {

		URL aURL =this.getClass().getResource(fileName);
		System.out.println("aURL :"+aURL.toString());
		File jsonDataFile = null;
		try {
			jsonDataFile = new File(aURL.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		System.out.println("file :"+jsonDataFile.toString());

		//File jsonDataFile = new File((this.getClass().getResource((fileName)).getFile()));
		
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
	
	private static String getId(String deviceUrl) {
		StringBuilder sb = new StringBuilder(deviceUrl);
        sb.delete(0, (sb.indexOf("/", 1)) + 9);
        return sb.toString();
	}
	
}
