package eu.linksmart.lc.wrapper;

import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Resource;
import eu.linksmart.lc.sc.types.Service;
import eu.linksmart.lc.wrapper.CatalogsClient;

import org.junit.Test;

import static org.junit.Assert.*;

public class CatalogsClientTest {
	
	private String BASE_RC_URL = "http://localhost:8081/rc";
	private String BASE_SC_URL = "http://localhost:8082/sc";

	@Test
    public void testCatalogsClient() {
		
		CatalogsClient catalog = new CatalogsClient(BASE_RC_URL, BASE_SC_URL);
		
		Registration registration = catalog.createResource("satis-device", "satis-resource", "tcp://localhost:1883", "/topic1/resourcemqtt");
    	
		assertTrue(catalog.registerResource(registration));
		
		Resource resource = catalog.getResource("satis-resource");
		assertNotNull(resource);
		System.out.println(("resource-topic: " + resource.getProtocols().get(0).getEndpoint().getTopic()));
		
		assertTrue(catalog.deleteResource(registration));
		
		eu.linksmart.lc.sc.types.Registration sregistration = catalog.createService("satis-service", "http://localhost:8080/service");
		
		assertTrue(catalog.registerService(sregistration));
		
		Service service = catalog.getService("satis-service");
		assertNotNull(service);
		System.out.println(("service-url: " + service.getProtocols().get(0).getEndpoint().getURL()));
		
		assertTrue(catalog.deleteService(sregistration));
		
    }
}
