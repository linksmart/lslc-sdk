package eu.linksmart.lc.wrapper.sample;

import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;

import eu.linksmart.lc.rc.types.Resource;
import eu.linksmart.lc.rc.types.Resources;
import eu.linksmart.lc.wrapper.BrokerClient;
import eu.linksmart.lc.wrapper.CatalogsClient;
import eu.linksmart.lc.wrapper.SenMLEntry;
import eu.linksmart.lc.wrapper.SenMLMessage;

public class Subscriber {
	
    public Subscriber() {
		
		String rcURL = "http://localhost:8081/rc";
		String scURL = "http://localhost:8082/sc";
		
		//
		// instantiate catalog client
		//
		CatalogsClient catalog = new CatalogsClient(rcURL, scURL);
		
		//
		// assuming that resource is already registered into Resource Catalog, therefore, registration documnent is retrieved from 
		// Resource Catalog based on the "resource name" and parse it for the contextualization information
		//
		Resource resource = catalog.getResource("satisfactory-resource").getResources().get(0);
		
		//
		// now parse the JSON document and know about the topic on which the events are being published by this resource
		//
		String brokerURL = resource.getProtocols().get(0).getEndpoint().getUrl();
		String topic = resource.getProtocols().get(0).getEndpoint().getPubTopic();
		
		//
		// instantiate broker Callback
		//
		Notifier client = new Notifier(brokerURL);
		
		client.subscribe(topic);
		
		//
		// broker client is disconnected so that resources associated with connection can be set free
		//
		client.disconnect();
		
	}
	
	class Notifier extends BrokerClient {

		public Notifier(String brokerUrl) {
	    	super(brokerUrl);
	    }
		
		@Override
	    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
			SenMLMessage senml = new Gson().fromJson(mqttMessage.toString(), SenMLMessage.class);
			ArrayList<SenMLEntry> entries = senml.getEntries();
			for (SenMLEntry entry : entries) {
	            System.out.println("notifier: topic - " + topic + " - " + entry.getName() + " - " + entry.getValue());
	        }
	    }
	}

}
