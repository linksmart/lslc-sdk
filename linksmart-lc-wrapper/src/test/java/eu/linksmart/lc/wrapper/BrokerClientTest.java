package eu.linksmart.lc.wrapper;

import org.junit.Test;

import eu.linksmart.lc.wrapper.BrokerClient;

public class BrokerClientTest {
	
	//@Test
    public void testBrokerClient() {
		
		BrokerClient brokerClient = new BrokerClient("tcp://localhost:1883");
		
		String topic = "/satisfactory/dm";
		
		brokerClient.subscribe(topic);
		
		brokerClient.publish(topic, "test-broker-message");
		
		try { Thread.sleep(2000); } catch (InterruptedException e) {}
		
		brokerClient.disconnect();
		
		System.out.println("testBrokerClient sucessfully finished");
		
	}
}
