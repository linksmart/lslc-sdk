package eu.linksmart.ls.rc.client;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class DeviceBuilderTest {
	
	@Test
	public void testDeviceBuilder() {
		
		assertNotNull(DeviceBuilder.createRegistration("/registration.json"));
		
		File jsonFile = new File((this.getClass().getResource(("/registration.json")).getFile()));
		assertNotNull(DeviceBuilder.createRegistration(jsonFile));
		
		assertNotNull(DeviceBuilder.createRegistration("testdc/device-b", "device-b", "testdc/device-b/resource-b", "resource-b", "http://localhost:8080/"));	
	}
}
