package eu.linksmart.lc.sc.client;

import com.google.gson.Gson;
import eu.linksmart.lc.sc.types.Registration;
import eu.linksmart.lc.sc.types.SCatalog;
import eu.linksmart.lc.sc.types.Service;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ServiceCatalogTest {
	
	private String BASE_URL = "http://localhost:8082/sc";
	
	@Test
	public void testTypesBinding() {
		
		//
		// create service registration
		//
		Registration registration = ServiceBuilder.createRegistration("testserver", "MqttBroker-b", "tcp://testserver.com:1883");
		System.out.println("Gson generated registration json: " + new Gson().toJson(registration));
		
		//assertNotNull(ServiceBuilder.createRegistration("/registration.json"));
		
		//
		// set URL of the catalog service
		//
		ServiceCatalog.setURL(BASE_URL);
		
		//
		// add service registration
		//
		assertTrue(ServiceCatalog.add(registration));
		
		String serviceID = registration.getId();
		
		
		//
		// update service
		//
		assertTrue(ServiceCatalog.update(serviceID, registration));
		
		//
		// get service
		//
		Service service = ServiceCatalog.get(serviceID);
		System.out.println("get-service - id: " + service.getId() + " - name: " + service.getName());
		
		//
		// get services
		//
		SCatalog catalog = ServiceCatalog.getServices(1, 100);
		System.out.println("get-services - total: " + catalog.getServices().size());
		List<Service> services = catalog.getServices();
		for (int i = 0; i < services.size(); i++) {
            Service cservice = services.get(i);
            System.out.println("get-services - id: " + cservice.getId() + " - name: " + cservice.getName());
		}
		
		//
		// find service
		//
		Service searched_service = ServiceCatalog.findService("name", Comparison.EQUALS.getCriteria(), "MqttBroker-b");
		System.out.println("find-service - id: " + searched_service.getId());
		
		//
		// find services
		//
		SCatalog searched_services_catalog = ServiceCatalog.findServices("name", Comparison.EQUALS.getCriteria(), "MqttBroker-b", 1, 100);
		System.out.println("find-services: total: " + searched_services_catalog.getServices().size());
		List<Service> fservices = searched_services_catalog.getServices();
		for (int i = 0; i < fservices.size(); i++) {
            Service fservice = fservices.get(i);
            System.out.println("find-services - id: " + fservice.getId() + " - name: " + fservice.getName());
		}
		
		assertTrue(ServiceCatalog.delete(serviceID));
	}
}
