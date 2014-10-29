package eu.linksmart.lc.rc.client;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

import eu.linksmart.lc.rc.client.Comparison;
import eu.linksmart.lc.rc.client.ResourceCatalog;
import eu.linksmart.lc.rc.types.Catalog;
import eu.linksmart.lc.rc.types.Device;
import eu.linksmart.lc.rc.types.Endpoint;
import eu.linksmart.lc.rc.types.Meta;
import eu.linksmart.lc.rc.types.Protocol;
import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Representation;
import eu.linksmart.lc.rc.types.Resource;
import eu.linksmart.lc.rc.types.TextPlain;

public class CatalogTest {
	
	@Test
	public void testTypesBinding() {
		
		Registration registration = DeviceBuilder.createRegistration("testdc/device-b", "device-b", "testdc/device-b/resource-b", "resource-b", "http://localhost:8080/");
		
		System.out.println("Gson generated registration json: " + new Gson().toJson(registration));
		
		assertTrue(ResourceCatalog.registerDevice(registration));
		
		assertTrue(ResourceCatalog.updateDevice("testdc/device-b", registration));
		
		Catalog catalog = ResourceCatalog.getAllDevices();
		System.out.println("total devices: " + catalog.getDevices().size() + " - total resources: " + catalog.getResources().size());
		
		for (String name : catalog.getDevices().keySet()) {
            Device device = (Device) (catalog.getDevices().get(name));
            System.out.println("device id: " + device.getId());
		}
		
		Device device = ResourceCatalog.getDevice("testdc/device-b");
		System.out.println("get-device-name: " + device.getName());
		
		Resource resource = ResourceCatalog.getResource("testdc/device-b/resource-b");
		System.out.println("get-resource-name: " + resource.getName());
		
		Resource searched_resource = ResourceCatalog.search("name", Comparison.EQUALS.getCriteria(), "resource-b");
		System.out.println("searched-resource-name: " + searched_resource.getName());
		
		assertTrue(ResourceCatalog.deleteDevice("testdc/device-b"));
	}
	
	private Registration createRegistration() {
		
		Registration registration = new Registration();
		registration.setId("testdc/device-gen");
		registration.setType("Device");
		registration.setName("device-gen");
		registration.setDescription("Gson generated registration string");
		registration.setTtl(30);
		
		Meta meta = new Meta();
		meta.put("m1", "1");
		meta.put("m2", "2");
		registration.setMeta(meta);
		
		Resource resource = new Resource();
		resource.setId("testdc/device-gen/resource-gen");
		resource.setType("Resource");
		resource.setName("resource-gen");
		
		Meta resource_meta = new Meta();
		resource_meta.put("model", "abc");
		resource.setMeta(resource_meta);
		
		Protocol protocol = new Protocol();
		protocol.setType("REST");
		
		Endpoint endpoint = new Endpoint();
		endpoint.setURL("http://localhost:8080/");
		protocol.setEndpoint(endpoint);
		
		List<String> methodsList = new ArrayList<String>();
		methodsList.add("GET"); 
		protocol.setMethods(methodsList);
		
		List<String> contentTypeList = new ArrayList<String>();
		contentTypeList.add("text/plain"); 
		protocol.setContentTypes(contentTypeList);
		
		List<Protocol> protocolList = new ArrayList<Protocol>();
		protocolList.add(protocol);
		resource.setProtocols(protocolList);
		
		Representation representation = new Representation();
		TextPlain repre = new TextPlain();
		repre.setType("string");
		representation.setTextPlain(repre);
		resource.setRepresentation(representation);
		
		List<Resource> resources = new ArrayList<Resource>();
		resources.add(resource);
		registration.setResources(resources);
		
		return registration;
	}
}
