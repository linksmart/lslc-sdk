package eu.linksmart.lc.rc.client;

import eu.linksmart.lc.rc.types.Catalog;
import eu.linksmart.lc.rc.types.Device;
import eu.linksmart.lc.rc.types.Devices;
import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resource;
import eu.linksmart.lc.rc.types.Resources;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CatalogTest {
	
	private String BASE_URL = "http://localhost:8081";
	
	//@Test
	public void testAllResources() {
		
//		ResourceCatalog.setURL(BASE_URL);
//		
//		Registration registration = DeviceBuilder.createRegistration("testdc", "device-b", "resource-b", "http://localhost:8080/");
//		
//		String deviceID = registration.getId();
//		String resourceID = registration.getResources().get(0).getId();
//		
//		assertTrue(ResourceCatalog.add(registration));
		
//		Device device = ResourceCatalog.get(deviceID);
//		System.out.println("get-device - id: " + device.getId() + " - name: " + device.getName());
//		
//		Resource resource = ResourceCatalog.getResource(resourceID);
//		System.out.println("get-resource - id: " + resource.getId() + " - device-id: " + resource.getDevice());
//		
//		//
//		// get devices
//		//
//		Catalog catalog = ResourceCatalog.findResources("id", Comparison.CONTAINS.getCriteria(), "satisfactory", 1, 100);
//		System.out.println(catalog.getResources().size());
//		List<Resource> gdresources = catalog.getResources();
//		for (int i = 0; i < gdresources.size(); i++) {
//			Resource searched_resource = (Resource) (gdresources.get(i));
//            System.out.println("get-devices - resource-id: " + searched_resource.getId() + " - device-id: " + searched_resource.getDevice());
//		}
		
//		String catalogJsonString = ResourceCatalogClient.getInstance("http://160.40.2.206:8081/rc").getDevices(1, 100);
//		Gson gson = new Gson();
//		JsonParser parser = new JsonParser();
//		JsonObject jObject = parser.parse(catalogJsonString).getAsJsonObject();
//		JsonElement resourcesStr = jObject.get("resources");
//		JsonArray arr = resourcesStr.getAsJsonArray();
//		System.out.println(arr.size());
//		for(JsonElement res : arr) {
//			System.out.println(res);
//			Resource resource = gson.fromJson(res,Resource.class);
//			System.out.println(resource.getId());
//		}
		
		
//		Catalog catalog = ResourceCatalog.getDevices(1,100);
//		//Catalog catalog = ResourceCatalog.findDevices("id", Comparison.CONTAINS.getCriteria(), "satisfactory", 1, 100);
//		System.out.println("get-devices - total devices: " + catalog.getDevices().size() + " - total resources: " + catalog.getResources().size());
//		for (String key : catalog.getDevices().keySet()) {
//            Device searched_device = (Device) (catalog.getDevices().get(key));
//            System.out.println("get-devices - id: " + searched_device.getId());
//		}
//		List<Resource> gdresources = catalog.getResources();
//		for (int i = 0; i < gdresources.size(); i++) {
//			Resource searched_resource = (Resource) (gdresources.get(i));
//            System.out.println("get-devices - resource-id: " + searched_resource.getId() + " - device-id: " + searched_resource.getDevice());
//		}
		
		//assertTrue(ResourceCatalog.delete(deviceID));
			
	}
	
	@Test
	public void testResourceCatalog() {
		
		//
		// creating registration
		//
		assertNotNull(DeviceBuilder.createRegistration("/registration.json"));
		assertNotNull(DeviceBuilder.createRegistration("device1", "resource1", "http://localhost:8080/"));
		Registration registration = DeviceBuilder.createRegistration("device1", "resource1", "tcp://localhost:1883", "stopic");
		assertNotNull(registration);
		
		//
		// set URL of the catalog service
		//
		ResourceCatalog.setURL(BASE_URL);
		
		//
		// device by URL
		//
		String deviceUrl = ResourceCatalog.add(registration);
		assertNotNull(deviceUrl);
		Device device = ResourceCatalog.get(deviceUrl);
		System.out.println("get-device - id: " + device.getId() + " - name: " + device.getName());
		assertTrue(ResourceCatalog.update(deviceUrl, registration));
		assertTrue(ResourceCatalog.delete(deviceUrl));
		
		//
		// device by ID
		//
		String deviceId = "sampleId";
		assertNotNull(ResourceCatalog.add(deviceId, registration));
		Device device1 = ResourceCatalog.getById(deviceId);
		System.out.println("get-device-by-id:" + device1.getId() + "-name:" + device1.getName() + "-url:" + device1.getUrl());
		assertTrue(ResourceCatalog.updateById(deviceId, registration));
		assertTrue(ResourceCatalog.deleteById(deviceId));
		
		assertNotNull(ResourceCatalog.add(registration));
		
		//
		// get/find devices
		//
		Catalog catalog = ResourceCatalog.getCatalogInfo();
		System.out.println("getCatalogInfo - total devices: " + catalog.getDevices() + " - total resources: " + catalog.getResources());
		
		Devices devices = ResourceCatalog.getDevices();
		System.out.println("getDevices - total devices: " + devices.getTotal());
		for (int i = 0; i < devices.getDevices().size(); i++) {
            Device searched_device = (Device) (devices.getDevices().get(i));
            System.out.println("get-devices - id: " + searched_device.getId());
            List<String> gdresources = searched_device.getResources();
            for (int j = 0; j < gdresources.size(); j++) {
                System.out.println("get-devices - resource-url: " + gdresources.get(j));
    		}
		}
		
		Devices ds = ResourceCatalog.getDevices(1,100);
		System.out.println("getDevices-pages - total devices: " + devices.getTotal());
		
		Devices fdevices = ResourceCatalog.findDevices("name", Comparison.EQUALS.getCriteria(), "device1");
		System.out.println("find-devices: " + fdevices.getDevices().size());
		
		Devices fdevices1 = ResourceCatalog.findDevices("name", Comparison.EQUALS.getCriteria(), "device1", 1, 100);
		System.out.println("find-devices-pages: " + fdevices1.getDevices().size());
		
		//
		// get/find resources
		//
		String resourceUrl = "resourceUrl";
		String resourceId = "resourceId";
		Resources resources = ResourceCatalog.getResources();
		System.out.println("get-resources - total: " + resources.getTotal());
		List<Resource> resourcList = resources.getResources();
		for (int i = 0; i < resourcList.size(); i++) {
			Resource resource = resourcList.get(i);
			System.out.println("get-resources - id: " + resource.getId() + " - url: " + resource.getUrl());
			resourceUrl = resource.getUrl();
			resourceId = resource.getId();
		}
		
		Resources rs = ResourceCatalog.getResources(1,100);
		System.out.println("get-resources-pages - total: " + rs.getTotal());
		
		
		Resource resource = ResourceCatalog.getResource(resourceUrl);
		System.out.println("get-resource - id: " + resource.getUrl() + " - device: " + resource.getDevice());
		
		Resource res = ResourceCatalog.getResourceById(resourceId);
		System.out.println("get-resource-by_id: " + res.getUrl() + " - device: " + res.getDevice());
		
		Resources fresources = ResourceCatalog.findResources("name", Comparison.EQUALS.getCriteria(), "resource1");
		System.out.println("find-resources - total: " + fresources.getTotal());
		
		Resources fresources1 = ResourceCatalog.findResources("name", Comparison.EQUALS.getCriteria(), "resource1", 1, 100);
		System.out.println("find-resources-pages: total: " + fresources.getResources().size());
		
	}
	
	@Test
	public void testTypesBinding() {
		
		String deviceJson = readFileContents("/registration.json");
		
		String registration_url = ResourceCatalogClient.getInstance(BASE_URL).add(deviceJson);
		assertNotNull(registration_url);
		
		String catalogJsonString = ResourceCatalogClient.getInstance(BASE_URL).getCatalogInfo();
		System.out.println("catalog: " + catalogJsonString);
		Catalog catalog = new Gson().fromJson(catalogJsonString, Catalog.class);
		System.out.println(catalog.getDevices() + "-" + catalog.getResources());
		
		String catalogDevicesString = ResourceCatalogClient.getInstance(BASE_URL).getDevices();
		System.out.println("catalog-Devices: " + catalogDevicesString);
		Devices cdevices = new Gson().fromJson(catalogDevicesString, Devices.class);
		System.out.println(cdevices.getTotal() + "-" +  cdevices.getDevices().get(0).getId());
		
		String catalogDeviceString = ResourceCatalogClient.getInstance(BASE_URL).getById(cdevices.getDevices().get(0).getId());
		System.out.println("catalog-Device: " + catalogDeviceString);
		Device cdevice = new Gson().fromJson(catalogDeviceString, Device.class);
		System.out.println(cdevice.getName() + "-" +  cdevice.getResources().get(0));
		
		String catalogfindDevice = ResourceCatalogClient.getInstance(BASE_URL).findDevices("name", "equals", cdevices.getDevices().get(0).getName());
		System.out.println("catalog-findDevice: " + catalogfindDevice);
		Devices fcdevices = new Gson().fromJson(catalogfindDevice, Devices.class);
		System.out.println(fcdevices.getTotal() + "-" +  fcdevices.getDevices().get(0).getResources().get(0));
		
		String catalogresources = ResourceCatalogClient.getInstance(BASE_URL).getResources();
		System.out.println("catalog-resources: " + catalogresources);
		Resources cresources = new Gson().fromJson(catalogresources, Resources.class);
		System.out.println(cresources.getTotal() + "-" +  cresources.getResources().size());
		
		String catalogresource = ResourceCatalogClient.getInstance(BASE_URL).getResource(cresources.getResources().get(0).getUrl());
		System.out.println("catalog-resource: " + catalogresource);
		Resource cresource = new Gson().fromJson(catalogresource, Resource.class);
		System.out.println(cresource.getName() + "-" +  cresource.getProtocols().get(0).getType());
		
		String catalogfindResource = ResourceCatalogClient.getInstance(BASE_URL).findResources("name", "equals", cresources.getResources().get(0).getName());
		System.out.println("catalog-findResource: " + catalogfindResource);
		Resources fcresources = new Gson().fromJson(catalogfindResource, Resources.class);
		System.out.println(fcresources.getTotal() + "-" +  fcresources.getResources().get(0).getProtocols().get(0).getEndpoint().getUrl());
			
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
	
}
