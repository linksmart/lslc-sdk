package eu.linksmart.lc.wrapper.sample;

import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.sc.types.Service;
import eu.linksmart.lc.wrapper.BrokerClient;
import eu.linksmart.lc.wrapper.CatalogsClient;

public class Publisher {
	
    public Publisher() {
		
    	//
    	// resource catalog URL
    	//
		String rcURL = "http://localhost:8081/rc";
		
		//
		// service catalog URL
		//
		String scURL = "http://localhost:8082/sc";
		
		//
		// event broker URL
		//
		String brokerURL = "tcp://localhost:1883";
		
		//
		// topic on which the events are published
		//
		String topic = "/satisfactory/device1/test";
		
		//
		// instantiate the catalogs client with resource and service catalog URLs
		//
		CatalogsClient catalog = new CatalogsClient(rcURL, scURL);
		
		Service resourceCatalog = catalog.getService("ResourceCatalog");
		System.out.println("resource-catalog: " + resourceCatalog.getDescription());
		//
		// create registration template for a device
		//
		Registration registration = catalog.createResource("satisfactory-device", "satisfactory-resource", brokerURL, topic);
    	
		//
		// add this resource registration into resource catalog, 
		// once registered, other services can discover or lookup this service
		// by different search criterias, e.g., resource name
		//
		catalog.registerResource(registration);
		
		//
		// instantiate the broker client
		//
		BrokerClient brokerClient = new BrokerClient(brokerURL);
		
		//
		// now publish a string to the given topic
		//
		brokerClient.publish(topic, "test-broker-message");
		
		//
		// resource should be deleted from resource catalog before exiting the program since, by default, TTL value in resource registration is set to -1 
		// so that registration is alive in resource catalog all the time, however, one can set another TTL value
		//
		catalog.deleteResource(registration);
		
		//
		// broker client is disconnected so that resources associated with connection can be set free
		//
		brokerClient.disconnect();	
	}
}
