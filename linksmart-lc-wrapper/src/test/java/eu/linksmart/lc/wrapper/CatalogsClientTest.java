package eu.linksmart.lc.wrapper;

import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resource;
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
		
		Service resourceCatalog = catalog.getService("ResourceCatalog");
		System.out.println("resource-catalog URL: " + resourceCatalog.getProtocols().get(0).getEndpoint().getURL());
		
		//Service eventBroker = catalog.getService("MqttBroker");
		//System.out.println("broker-url: " + eventBroker.getProtocols().get(0).getEndpoint().getURL());
		
		Registration registration = catalog.createResource("sample-device", "sample-resource", "tcp://localhost:1883", "/topic1/resourcemqtt");
    	
		System.out.println("resource: " + new Gson().toJson(registration).toString());
		
		assertTrue(catalog.registerResource(registration));
		
		Resource resource = catalog.getResource("sample-resource");
		assertNotNull(resource);
		System.out.println(("resource-topic: " + resource.getProtocols().get(0).getEndpoint().getTopic()));
		
		assertTrue(catalog.deleteResource(registration));
		
		eu.linksmart.lc.sc.types.Registration sregistration = catalog.createService("sample-service", "http://localhost:8080/service");
		
		System.out.println("service: " + new Gson().toJson(sregistration).toString());
		
		assertTrue(catalog.registerService(sregistration));
		
		Service service = catalog.getService("sample-service");
		assertNotNull(service);
		System.out.println(("service-url: " + service.getProtocols().get(0).getEndpoint().getURL()));
		
		assertTrue(catalog.deleteService(sregistration));
		
		System.out.println("testCatalogsClient sucessfully finished");
		
    }
}
