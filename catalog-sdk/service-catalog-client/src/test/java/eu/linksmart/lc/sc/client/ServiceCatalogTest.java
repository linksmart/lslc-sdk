package eu.linksmart.lc.sc.client;

import com.google.gson.Gson;

import eu.linksmart.lc.sc.types.Registration;
import eu.linksmart.lc.sc.types.SCatalog;
import eu.linksmart.lc.sc.types.Service;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServiceCatalogTest {
	
	private String BASE_URL = "http://localhost:8082";
	
	@Test
	public void testTypesBinding() {
		
		//
		// create service registration
		//
		Registration registration = ServiceBuilder.createRegistration("MqttBroker", "tcp://testserver.com:1883");
		
		assertNotNull(ServiceBuilder.createRegistration("/registration.json"));
		
		//
		// set URL of the catalog service
		//
		ServiceCatalog.setURL(BASE_URL);
		
		//
		// get services
		//
		SCatalog catalog = new Gson().fromJson(ServiceCatalogClient.getInstance(BASE_URL).getServices(), SCatalog.class);
		assertNotNull(catalog);
		List<Service> services = catalog.getServices();
		for (int i = 0; i < services.size(); i++) {
            System.out.println("get-services - id: " + services.get(i).getId() + " - name: " + services.get(i).getName());
		}
			
		//
		// add service registration
		//
		String serviceUrl = ServiceCatalogClient.getInstance(BASE_URL).add(new Gson().toJson(registration));
		assertNotNull(serviceUrl);
		String serviceId = getServiceId(serviceUrl);
		
		//
		// get service
		//
		Service service = new Gson().fromJson(ServiceCatalogClient.getInstance(BASE_URL).get(serviceId), Service.class);
		assertNotNull(service);
		System.out.println("get-service - id: " + service.getId() + " - name: " + service.getName() + " - url: " + service.getUrl());
		
		//
		// update service
		//
		assertTrue(ServiceCatalogClient.getInstance(BASE_URL).update(serviceId, new Gson().toJson(registration)));
		
		//
		// find services
		//
		SCatalog fscatalog = new Gson().fromJson(ServiceCatalogClient.getInstance(BASE_URL).findServices("name", Comparison.EQUALS.getCriteria(), "MqttBroker"), SCatalog.class);
		assertNotNull(fscatalog);
		List<Service> fservices = fscatalog.getServices();
		for (int i = 0; i < fservices.size(); i++) {
            System.out.println("find-services - id: " + fservices.get(i).getId() + " - name: " + fservices.get(i).getName());
		}
		
		assertTrue(ServiceCatalog.delete(serviceId));
	}
	
	private String getServiceId(String serviceUrl) {
		StringBuilder sb = new StringBuilder(serviceUrl);
        sb.delete(0, (sb.indexOf("/", 1)) + 1);
        return sb.toString();
	}

}
