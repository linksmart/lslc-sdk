package eu.linksmart.lc.wrapper;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokerClient implements MqttCallback {
	
	private final Logger LOG = LoggerFactory.getLogger(BrokerClient.class);

    private MqttClient mqttClient;

    private String brokerEndpoint;
	private String mqttUsername;
	private String mqttPasword;
    private int qos = 0;
    private String clientId = "ls-mqtt-client-" + System.currentTimeMillis();
    
    public BrokerClient(String brokerEndpoint) {
    	this.brokerEndpoint = brokerEndpoint;
    	connect();
    }
    
    public BrokerClient(String brokerEndpoint, String mqttUsername, String mqttPasword, int qos) {
    	this.brokerEndpoint = brokerEndpoint;
    	this.mqttUsername = mqttUsername;
    	this.mqttPasword = mqttPasword;
    	this.qos = qos;
    	connect();
    }
    
    public void connect() {
    	
    	if (mqttClient != null) {
    		if(mqttClient.isConnected())
    			return;
    	}
    	
    	try {
    		initBrokerConnection();
        } catch (MqttException e) {
        	LOG.error("unable to connect to the mqtt broker: " + e.getMessage());
        }
    	
        if (mqttClient != null && mqttClient.isConnected()) 
            mqttClient.setCallback(this);
    	
    }
    
    private void initBrokerConnection() throws MqttException {
    	
    	LOG.info("connecting to the mqtt broker: " + brokerEndpoint);
    	
        MemoryPersistence persistence = new MemoryPersistence();
        
        mqttClient = new MqttClient(brokerEndpoint, clientId, persistence);
        
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        
        if (mqttUsername != null && mqttPasword != null) {
            connOpts.setUserName(mqttUsername);
            connOpts.setPassword(mqttPasword.toCharArray());
        }
        
        mqttClient.connect(connOpts);
        
        LOG.info("connected to the mqtt broker: " + mqttClient.isConnected() + " - " + clientId);
    }
    
    public void publish(final String mqttTopic, final String message) {
    	publish(mqttTopic, message.getBytes());
    }
    
    public void publish(final String mqttTopic, final byte[] payload) {
    	if (mqttClient == null || !(mqttClient.isConnected())) {
    		LOG.error("mqtt client is not connected, can't publish to the topic [" + mqttTopic + "]");
    		return;
    	}
    			
    	if (payload != null) {
            try {
            	mqttClient.publish(mqttTopic, payload, this.qos, false);
            } catch (MqttException e) {
                LOG.error("error publishing a message to the mqtt broker reason: " + e.getMessage());
            }
        }
    }
    
    public void subscribe(final String mqttTopic) {
    	if (mqttClient == null || !(mqttClient.isConnected())) {
    		LOG.error("mqtt client is not connected, can't subscribe to the topic [" + mqttTopic + "]");
    		return;
    	}
    	
    	try {
			mqttClient.subscribe(mqttTopic);
		} catch (MqttException e) {
			LOG.error("unable to subscribe to a topic [" + mqttTopic + "] reason: " + e.getMessage());
		}
    }
    
    public void subscribe(final String[] mqttTopics) {
    	if (mqttClient == null || !(mqttClient.isConnected())) {
    		LOG.error("mqtt client is not connected, can't subscribe with topics array [" + mqttTopics.length + "]");
    		return;
    	}
    	
    	try {
			mqttClient.subscribe(mqttTopics);
		} catch (MqttException e) {
			LOG.error("unable to subscribe to topics [" + mqttTopics + "] reason: " + e.getMessage());
		}
    }
    
    public void disconnect() {
    	if(mqttClient != null) {
    		if (mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                } catch (MqttException e) {
                	LOG.error("error disconnecting from the event broker: " + e.getMessage());
                }
                LOG.error("disconnected from the mqtt broker - " + clientId);
            }
    	}
    }
    
    @Override
    public void connectionLost(Throwable throwable) {
        LOG.error("lost connection to the mqtt broker. will try to reconnect");
        boolean isConnected = false;
        int numOfTries = 0;
        int maxNumOfTries = 10;
        while (!isConnected) {
        	if(numOfTries > maxNumOfTries) {
        		LOG.error("unable to connect to the broker in maximum number of tries: " + maxNumOfTries);
        		return;
          	}
        	numOfTries++;
            try {
            	initBrokerConnection();
                if (mqttClient != null && mqttClient.isConnected()) {
                    isConnected = true;
                    mqttClient.setCallback(this);
                }
                else 
                    LOG.error("could not connect to the mqtt broker. will retry ...");
            } catch (MqttException e) {
                LOG.error("error connecting to the mqtt broker. will retry in 3 seconds ...");
            }
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                LOG.error("interrupted while trying to reconnect");
                return;
            }
        }
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
    	// here comes the notifications for subscriptions
    	// one can also implement own callback handler 
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}
    
}
