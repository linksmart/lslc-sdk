package eu.linksmart.lc.sc.client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gson.Gson;

import eu.linksmart.lc.sc.types.Registration;
import eu.linksmart.lc.sc.types.SCatalog;
import eu.linksmart.lc.sc.types.Service;

public class ServiceCatalogTest {
	
	@Test
	public void testTypesBinding() {
		
		Registration registration = ServiceBuilder.createRegistration("testserver", "MqttBroker-b", "tcp://testserver.com:1883");
		System.out.println("Gson generated registration json: " + new Gson().toJson(registration));
		
		assertTrue(ServiceCatalog.add(registration));
		
		String serviceID = registration.getId();
		
		assertTrue(ServiceCatalog.update(serviceID, registration));
		
		Service service = ServiceCatalog.get(serviceID);
		System.out.println("get-service-name: " + service.getName());
		
		SCatalog catalog = ServiceCatalog.getServices(1, 100);
		System.out.println("total services: " + catalog.getServices().size());
		
		Service searched_service = ServiceCatalog.findService("name", Comparison.EQUALS.getCriteria(), "MqttBroker-b");
		System.out.println("searched-service-name: " + searched_service.getName());
		
		SCatalog searched_services_catalog = ServiceCatalog.findServices("name", Comparison.EQUALS.getCriteria(), "MqttBroker-b", 1, 100);
		System.out.println("searched-services: " + searched_services_catalog.getServices().size());
		
		assertTrue(ServiceCatalog.delete(serviceID));
	}
}
