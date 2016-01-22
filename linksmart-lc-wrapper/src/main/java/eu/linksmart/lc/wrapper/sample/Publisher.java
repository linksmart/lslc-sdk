package eu.linksmart.lc.wrapper.sample;

import java.util.Properties;

import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.wrapper.BrokerClient;
import eu.linksmart.lc.wrapper.CatalogsClient;

public class Publisher {
	
    public Publisher() {
		
		String rcURL = "http://localhost:8081/rc";
		String scURL = "http://localhost:8082/sc";
		
		String brokerURL = "tcp://localhost:1883";
		String topic = "/satisfactory/device1/test";
		
		//
		// instantiate catalog client
		//
		CatalogsClient catalog = new CatalogsClient(rcURL, scURL);
		
		//
		// create registration for resource
		//
		Registration registration = catalog.createResource("satisfactory-device", "satisfactory-resource", brokerURL, topic);
    	
		//
		// add this registration into Resource Catalog
		//
		catalog.registerResource(registration);
		
		//
		// instantiate broker client
		//
		BrokerClient brokerClient = new BrokerClient(brokerURL);
		
		//
		// now publish a string to the given topic
		//
		brokerClient.publish(topic, "test-broker-message");
		
		//
		// one can also publish key-value pairs, SenML based JSON document is being sent
		//
		Properties props = new Properties();
		props.setProperty("sample-key", "sameple-value");
		brokerClient.publish(topic, props);
		
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
