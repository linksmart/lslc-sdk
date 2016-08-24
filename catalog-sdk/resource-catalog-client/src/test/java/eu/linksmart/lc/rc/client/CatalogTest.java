package eu.linksmart.lc.rc.client;

import eu.linksmart.lc.rc.types.Catalog;
import eu.linksmart.lc.rc.types.Device;
import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resource;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class CatalogTest {
	
	private String BASE_URL = "http://localhost:8081/rc";
	
	//@Test
	public void testAllResources() {
		
		ResourceCatalog.setURL("http://localhost:8081/rc");
		
//		Registration registration = DeviceBuilder.createRegistration("testdc", "device-b", "resource-b", "http://localhost:8080/");
//		
//		String deviceID = registration.getId();
//		String resourceID = registration.getResources().get(0).getId();
//		
//		assertTrue(ResourceCatalog.add(registration));
//		
//		Device device = ResourceCatalog.get(deviceID);
//		System.out.println("get-device - id: " + device.getId() + " - name: " + device.getName());
//		
//		Resource resource = ResourceCatalog.getResource(resourceID);
//		System.out.println("get-resource - id: " + resource.getId() + " - device-id: " + resource.getDevice());
		
		//
		// get devices
		//
		Catalog catalog = ResourceCatalog.findResources("id", Comparison.CONTAINS.getCriteria(), "satisfactory", 1, 100);
		System.out.println(catalog.getResources().size());
		List<Resource> gdresources = catalog.getResources();
		for (int i = 0; i < gdresources.size(); i++) {
			Resource searched_resource = (Resource) (gdresources.get(i));
            System.out.println("get-devices - resource-id: " + searched_resource.getId() + " - device-id: " + searched_resource.getDevice());
		}
		
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
	
	//@Test
	public void testTypesBinding() {
		
		//
		// creating registration
		//
		Registration registration = DeviceBuilder.createRegistration("testdc", "device-b", "resource-b", "http://localhost:8080/");
		
		//assertNotNull(DeviceBuilder.createRegistration("/registration.json"));
		
		String deviceID = registration.getId();
		String resourceID = registration.getResources().get(0).getId();
		
		//
		// set URL of the catalog service
		//
		ResourceCatalog.setURL(BASE_URL);
		
		//
		// add device registration
		//
		assertTrue(ResourceCatalog.add(registration));
		
		//
		// update device registration
		//
		assertTrue(ResourceCatalog.update(deviceID, registration));
		
		//
		// get device
		//
		Device device = ResourceCatalog.get(deviceID);
		System.out.println("get-device - id: " + device.getId() + " - name: " + device.getName());
		
		//
		// get resource
		//
		Resource resource = ResourceCatalog.getResource(resourceID);
		System.out.println("get-resource - id: " + resource.getId() + " - device-id: " + resource.getDevice());
		
		//
		// get devices
		//
		Catalog catalog = ResourceCatalog.getDevices(1,100);
		System.out.println("get-devices - total devices: " + catalog.getDevices().size() + " - total resources: " + catalog.getResources().size());
		for (String key : catalog.getDevices().keySet()) {
            Device searched_device = (Device) (catalog.getDevices().get(key));
            System.out.println("get-devices - id: " + searched_device.getId());
		}
		List<Resource> gdresources = catalog.getResources();
		for (int i = 0; i < gdresources.size(); i++) {
			Resource searched_resource = (Resource) (gdresources.get(i));
            System.out.println("get-devices - resource-id: " + searched_resource.getId() + " - device-id: " + searched_resource.getDevice());
		}
		
		//
		// find device
		//
		Device sdevice = ResourceCatalog.findDevice("name", Comparison.EQUALS.getCriteria(), "device-b");
		System.out.println("find-device - id: " + sdevice.getId());
		
		//
		// find devices
		//
		Catalog sdcatalog = ResourceCatalog.findDevices("name", Comparison.EQUALS.getCriteria(), "device-b", 1, 100);
		System.out.println("find-devices: total devices: " + sdcatalog.getDevices().size() + " - total resources: " + sdcatalog.getResources().size());
		for (String key : sdcatalog.getDevices().keySet()) {
            Device searched_device = (Device) (sdcatalog.getDevices().get(key));
            System.out.println("findDevices - id: " + searched_device.getId());
		}
		List<Resource> sdsresources = sdcatalog.getResources();
		for (int i = 0; i < sdsresources.size(); i++) {
			Resource searched_resource = (Resource) (sdsresources.get(i));
            System.out.println("find-devices - resource-id: " + searched_resource.getId() + " - device-id: " + searched_resource.getDevice());
		}
		
		//
		// find resource
		//
		Resource sresource = ResourceCatalog.findResource("name", Comparison.EQUALS.getCriteria(), "resource-b");
		System.out.println("find-resource - id: " + sresource.getId() + " - device-id: " + sresource.getDevice());
		
		//
		// find resources
		//
		Catalog srcatalog = ResourceCatalog.findResources("name", Comparison.EQUALS.getCriteria(), "resource-b", 1, 100);
		System.out.println("find-resources: total resources: " + srcatalog.getResources().size());
		List<Resource> resources = srcatalog.getResources();
		for (int i = 0; i < resources.size(); i++) {
            Resource searched_resource = (Resource) (resources.get(i));
            System.out.println("find-resources - id: " + searched_resource.getId() + " - device" + searched_resource.getDevice());
		}
		
		//
		// delete device registration
		//
		assertTrue(ResourceCatalog.delete(deviceID));
	}
	
}
