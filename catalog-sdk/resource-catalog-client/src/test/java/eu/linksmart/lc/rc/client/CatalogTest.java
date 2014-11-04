package eu.linksmart.lc.rc.client;

import static org.junit.Assert.assertTrue;

import java.util.List;

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
		
		//
		// creating registration & adding
		//
		Registration registration = DeviceBuilder.createRegistration("testdc", "device-b", "resource-b", "http://localhost:8080/");
		
		System.out.println("Gson generated registration json: " + new Gson().toJson(registration));
		
		String deviceID = registration.getId();
		String resourceID = registration.getResources().get(0).getId();
		
		assertTrue(ResourceCatalog.add(registration));
		
		assertTrue(ResourceCatalog.update(deviceID, registration));
		
		Device device = ResourceCatalog.get(deviceID);
		System.out.println("get-device - id: " + device.getId() + " - name: " + device.getName());
		
		Resource resource = ResourceCatalog.getResource(resourceID);
		System.out.println("get-resource - id: " + resource.getId() + " - device-id: " + resource.getDevice());
		
		//
		// get devices
		//
		Catalog catalog = ResourceCatalog.getDevices(1,100);
		System.out.println("get-devices - total devices: " + catalog.getDevices().size() + " - total resources: " + catalog.getResources().size());
		for (String name : catalog.getDevices().keySet()) {
            Device searched_device = (Device) (catalog.getDevices().get(name));
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
		for (String name : sdcatalog.getDevices().keySet()) {
            Device searched_device = (Device) (sdcatalog.getDevices().get(name));
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
		
		assertTrue(ResourceCatalog.delete(deviceID));
	}
	
}
