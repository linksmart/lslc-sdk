package eu.linksmart.lc.rc.client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gson.Gson;

import eu.linksmart.lc.rc.client.Comparison;
import eu.linksmart.lc.rc.client.ResourceCatalog;
import eu.linksmart.lc.rc.types.Catalog;
import eu.linksmart.lc.rc.types.Device;
import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resource;

public class CatalogTest {
	
	@Test
	public void testTypesBinding() {
		
		Registration registration = DeviceBuilder.createRegistration("testdc", "device-b", "resource-b", "http://localhost:8080/");
		
		System.out.println("Gson generated registration json: " + new Gson().toJson(registration));
		
		String deviceID = registration.getId();
		String resourceID = registration.getResources().get(0).getId();
		
		assertTrue(ResourceCatalog.add(registration));
		
		assertTrue(ResourceCatalog.update(deviceID, registration));
		
		Device device = ResourceCatalog.get(deviceID);
		System.out.println("get-device-name: " + device.getName());
		
		Resource resource = ResourceCatalog.getResource(resourceID);
		System.out.println("get-resource-name: " + resource.getName());
		
		
		Catalog catalog = ResourceCatalog.getDevices(1,100);
		System.out.println("total devices: " + catalog.getDevices().size() + " - total resources: " + catalog.getResources().size());
		
		for (String name : catalog.getDevices().keySet()) {
            Device searched_device = (Device) (catalog.getDevices().get(name));
            System.out.println("device id: " + searched_device.getId());
		}
		
		Device sdevice = ResourceCatalog.findDevice("name", Comparison.EQUALS.getCriteria(), "device-b");
		System.out.println("searched-device-name: " + sdevice.getName());
		
		Catalog sdcatalog = ResourceCatalog.findDevices("name", Comparison.EQUALS.getCriteria(), "device-b", 1, 100);
		System.out.println("searched-devices: " + sdcatalog.getDevices().size());
		
		Resource sresource = ResourceCatalog.findResource("name", Comparison.EQUALS.getCriteria(), "resource-b");
		System.out.println("searched-resource-name: " + sresource.getName());
		
		Catalog srcatalog = ResourceCatalog.findResources("name", Comparison.EQUALS.getCriteria(), "resource-b", 1, 100);
		System.out.println("searched-resources: " + srcatalog.getResources().size());
		
		assertTrue(ResourceCatalog.delete(deviceID));
	}
}
