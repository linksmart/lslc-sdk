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
		
		assertTrue(ServiceCatalog.registerService(registration));
		
		String serviceID = registration.getId();
		
		assertTrue(ServiceCatalog.updateService(serviceID, registration));
		
		SCatalog catalog = ServiceCatalog.getAllServices();
		System.out.println("total services: " + catalog.getServices().size());
		
		Service service = ServiceCatalog.getService(serviceID);
		System.out.println("get-service-name: " + service.getName());
		
		Service searched_service = ServiceCatalog.search("service", "name", Comparison.EQUALS.getCriteria(), "MqttBroker");
		System.out.println("searched-service-name: " + searched_service.getName());
		
		assertTrue(ServiceCatalog.deleteService(serviceID));
	}
}
