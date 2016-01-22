package eu.linksmart.lc.rc.client;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.linksmart.lc.rc.types.Registration;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

public class DeviceBuilderTest {
	
	@Test
	public void testDeviceBuilder() {
		
		assertNotNull(DeviceBuilder.createRegistration("/registration.json"));

		URL aURL =this.getClass().getResource("/registration.json");
		System.out.println("aURL :"+aURL.toString());
		File jsonFile = null;
		try {
			jsonFile = new File(aURL.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		//File jsonFile = new File((this.getClass().getResource(("/registration.json")).getFile()));
		assertNotNull(DeviceBuilder.createRegistration(jsonFile));
		
		assertNotNull(DeviceBuilder.createRegistration("testdc", "device-b", "resource-b", "http://localhost:8080/"));	
		
		Registration mqttReg = DeviceBuilder.createRegistration("testdm", "device-mqtt", "resource-mqtt", "tcp://localhost:1883", "/topic1/resourcemqtt");
		
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(mqttReg));
		assertNotNull(mqttReg);
	}
}
