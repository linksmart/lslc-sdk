package eu.linksmart.local.sdk.testing;

import eu.linksmart.local.sdk.*;
import org.junit.Test;

import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by carlos on 27.10.14.
 */
public class TestJavaSDK {

    @Test
    public void testRemoteRegistration() throws ParseException, MalformedURLException {
        //setup device class instance

        String deviceID = "test-dc/DeviceA";
        UUID uuid = UUID.randomUUID();

        ContentType aContentType = new ContentType("text/plain");

        LSLCDevice device = new LSLCDevice();
        device.name = new String("DeviceA");
        device.description = new String("Device registered from Java DeviceManagementClient");
        device.ttl = 60;


        LSLCProtocol protocol = new LSLCProtocol();
        LSLCRepresentation representation = new LSLCRepresentation();
        LSLCResource resource = new LSLCResource();

        representation.contentType = aContentType;
        representation.type = new String("number");

        protocol.endpoint = new URL("http://device_a");
        protocol.type = new String("REST");
        protocol.methods.add("GET");
        protocol.contentTypes.add(new ContentType("text/plain"));

        resource.device = new String("device-a");
        resource.id = new String(deviceID+"/"+uuid);
        resource.name = new String("Resource A");
        resource.representation = representation;
        resource.protocols.add(protocol);

        device.resources.add(resource);


        // register device with RC

        DeviceManagementClient client = new DeviceManagementClient(new URL("http://localhost:7778/rc/"));
        //DeviceManagementClient client = new DeviceManagementClient(new URL("http://gando.fit.fraunhofer.de:8091/rc/"));

        client.registerDevice(device);



    }



}
