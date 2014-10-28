package eu.linksmart.local.sdk.testing;

import eu.linksmart.local.sdk.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by carlos on 27.10.14.
 */
public class TestJavaSDK {

    public LSLCDevice device;
    public String deviceID;

    @Before
    public void setupDeviceRegistrationDocument() throws MalformedURLException, ParseException {
        String hostID = "localhost";

        try {
            hostID = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        deviceID = hostID+"/DeviceA";
        UUID uuid = UUID.randomUUID();

        ContentType aContentType = new ContentType("text/plain");

        device = new LSLCDevice();
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

    }

    @Ignore
    public void testRemoteRegistrationAndUnregistration() throws MalformedURLException {

        // register device with RC

        //DeviceManagementClient client = new DeviceManagementClient(new URL("http://localhost:7778/rc/"));
        DeviceManagementClient client = new DeviceManagementClient(new URL("http://gando.fit.fraunhofer.de:8091/rc/"));

        assertTrue("could not register device", client.registerDevice(device));

        // unregister device from RC

        assertTrue("could not remove ",client.removeDevice(deviceID));



    }

    @Test
    public void getDeviceFromRC() throws MalformedURLException {

        //DeviceManagementClient client = new DeviceManagementClient(new URL("http://localhost:7778/rc/"));
        DeviceManagementClient client = new DeviceManagementClient(new URL("http://gando.fit.fraunhofer.de:8091/rc/"));

        client.registerDevice(device);

        client.getDevice(deviceID);

        // test one field
        assertEquals(device.resources.get(0).protocols.get(0).methods.get(0),"GET");

        client.removeDevice(deviceID);



    }



}
