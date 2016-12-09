package eu.linksmart.lc.wrapper;

import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resource;
import eu.linksmart.lc.sc.types.SCatalog;
import eu.linksmart.lc.sc.types.Service;
import eu.linksmart.lc.wrapper.CatalogsClient;

import org.junit.Test;

import com.google.gson.Gson;

import static org.junit.Assert.*;

public class CatalogsClientTest {
	
	private String BASE_RC_URL = "http://localhost:8081/rc";
	private String BASE_SC_URL = "http://localhost:8082/sc";

	//@Test
    public void testCatalogsClient() {
		
		CatalogsClient catalog = new CatalogsClient(BASE_RC_URL, BASE_SC_URL);
		
		SCatalog sCatalog = catalog.getService("ResourceCatalog");
		System.out.println("resource-catalog URL: " + sCatalog.getServices().get(0).getProtocols().get(0).getEndpoint().getUrl());
		
		//Service eventBroker = catalog.getService("MqttBroker");
		//System.out.println("broker-url: " + eventBroker.getProtocols().get(0).getEndpoint().getURL());
		
		Registration registration = catalog.createResource("sample-device", "sample-resource", "tcp://localhost:1883", "/topic1/resourcemqtt");
    	
		System.out.println("resource: " + new Gson().toJson(registration).toString());
		
		String deviveUrl = catalog.registerResource(registration);
		assertNotNull(deviveUrl);
		
		Resource resource = catalog.getResource("sample-resource").getResources().get(0);
		assertNotNull(resource);
		System.out.println(("resource-topic: " + resource.getProtocols().get(0).getEndpoint().getPubTopic()));
		
		assertTrue(catalog.deleteResource(deviveUrl));
		
		eu.linksmart.lc.sc.types.Registration sregistration = catalog.createService("sample-service", "http://localhost:8080/service");
		
		System.out.println("service: " + new Gson().toJson(sregistration).toString());
		
		String serviceId = catalog.registerService(sregistration);
		assertNotNull(serviceId);
		
		SCatalog sCatalogservice = catalog.getService("sample-service");
		assertNotNull(sCatalogservice);
		System.out.println(("service-url: " + sCatalogservice.getServices().get(0).getProtocols().get(0).getEndpoint().getUrl()));
		
		assertTrue(catalog.deleteService(serviceId));
		
		System.out.println("testCatalogsClient sucessfully finished");
		
    }
}
