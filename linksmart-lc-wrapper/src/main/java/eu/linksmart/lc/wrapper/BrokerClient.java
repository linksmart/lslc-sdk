package eu.linksmart.lc.wrapper;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.google.gson.Gson;

import java.util.Properties;

public class BrokerClient implements MqttCallback {

    private MqttClient mqttClient;

    private String brokerUrl = "tcp://localhost:1883";
    
    private final String clientId = "satisfactory-fit-broker-client";
    private final int qos = 0;
    
    private boolean isConnected = false;

    public BrokerClient() {
    	connect();
    }
    
    public BrokerClient(String brokerUrl) {
    	this.brokerUrl = brokerUrl;
    	connect();
    }
    
    public void connect() {
    	
    	if (isConnected)
    		return;
    	
    	try {
    		initBrokerConnection();
        } catch (MqttException e) {
            System.out.println("error connecting to the event broker. will retry ...");
        }
    	
        if (mqttClient != null && mqttClient.isConnected()) {
            isConnected = true;
            mqttClient.setCallback(this);
        } else {
            System.out.println("Could not initialize broker client. Will retry ...");
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted while trying to reconnect");
            }
        }
    }
    
    private void initBrokerConnection() throws MqttException {
    	
        MemoryPersistence persistence = new MemoryPersistence();
        String clientID = clientId + "-" + System.currentTimeMillis();

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        // Authenticate if configured
        
        mqttClient = new MqttClient(brokerUrl, clientID, persistence);

        try {
            System.out.println("connecting to the event broker: " + brokerUrl);
            mqttClient.connect(connOpts);
            System.out.println("connected to the event broker");
        } catch(MqttException e) {
            e.printStackTrace();
            System.err.println("unable to connect to the event broker: " + e.getMessage());
        }
    }
    
    public void subscribe(final String mqttTopic) {
    	if (!isConnected) {
    		System.err.println("event broker is not connected, can't subscribe to a topic.");
    		return;
    	}
    	try {
			mqttClient.subscribe(mqttTopic);
		} catch (MqttException e) {
			System.err.println("unable to subscribe to a topic [" + mqttTopic + "] reason: " + e.getMessage());
		}
    }

    public void publish(final String mqttTopic, Properties props) {
        // Create SenML message and marshal it into JSON
        try {
        	SenMLMessage senMLMessage = new SenMLMessage(props);
            String strSenML = new Gson().toJson(senMLMessage);
            publish(mqttTopic, strSenML);
        } catch (Exception e) {
            System.err.printf("error parsing/marshalling message to SenML: %s **discarded**\n", e.getMessage());
        }  
    }
    
    public void publish(final String mqttTopic, final String message) {
    	publish(mqttTopic, message.getBytes());
    }
    
    private void publish(final String mqttTopic, byte[] payload) {
    	if (!isConnected) {
    		System.err.println("event broker is not connected, can't publish to the topic [" + mqttTopic + "]");
    		return;
    	}
    	if (payload != null) {
            try {
            	MqttMessage message = new MqttMessage(payload);
                message.setQos(qos);
            	mqttClient.publish(mqttTopic, message);
            } catch (MqttException e) {
                System.err.printf("error publishing a message to event broker: %s\n", e.getMessage());
            }
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("lost connection to the event broker. Will try to reconnect");
        isConnected = false;
        while (!isConnected) {
            try {
            	initBrokerConnection();
                if (mqttClient != null && mqttClient.isConnected()) {
                    isConnected = true;
                    mqttClient.setCallback(this);
                }
                else 
                    System.out.println("could not initialize event broker client. Will retry ...");
            } catch (MqttException e) {
                System.out.println("error connecting to the event broker. Will retry ...");
            }
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                System.out.println("interrupted while trying to reconnect");
            }
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
    	System.out.println("brokerClient-notify: topic - " + topic + " - msg - " + mqttMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}

    public void disconnect() {
    	if(isConnected) {
    		if (mqttClient != null && mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                    isConnected = false;
                } catch (MqttException e) {
                    System.err.printf("error disconnecting from the event broker: %s\n", e.getMessage());
                }
                System.out.println("disconnected from the event broker.");
            }
    	}
    }
}
