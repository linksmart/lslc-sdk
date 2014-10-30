package eu.linksmart.local.sdk.testing;

import eu.linksmart.local.sdk.*;
import org.junit.Before;
import org.junit.Test;

import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by carlos on 27.10.14.
 */
public class TestJavaSDK {

    public LSLCDevice device;
    public String deviceID;
    DeviceManagementClient client;

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

        //resource.device = new String("device-a");
        resource.id = new String(deviceID+"/"+uuid);
        resource.name = new String("Resource A");
        resource.representation = representation;
        resource.protocols.add(protocol);

        device.resources.add(resource);

        //client = new DeviceManagementClient(new URL("http://localhost:7778/rc"));
        client = new DeviceManagementClient(new URL("http://gando.fit.fraunhofer.de:8091/rc"));


    }

    @Test
    public void testRemoteRegistrationAndUnregistration() throws MalformedURLException {

        // register device with RC


        assertTrue("could not register device", client.registerDevice(device));

        // unregister device from RC

        assertTrue("could not remove ",client.removeDevice(deviceID));



    }

    @Test
    public void getDeviceByID() throws MalformedURLException {


        client.registerDevice(device);

        LSLCDevice deviceFromRC = client.getDevice(deviceID);
        client.removeDevice(deviceID);

        // test if all fields were retrieved properly
        assertTrue("returned device not equal to original one", isRetrivedInstanceIdentical(deviceFromRC));





    }
    @Test
    public void getAllDevicesFromRC() throws MalformedURLException {


        client.registerDevice(device);

        List<LSLCDevice> devices = client.getAllDevices();

        client.removeDevice(deviceID);


        //assertEquals("should return one device ",1,devices.size());

        // TODO look up for "my test" device. since the RC changed the ID a workaround is needed. fuuuUU
        for(int i=0; i < devices.size();i++){
            LSLCDevice d = devices.get(i);
            if(d.id.equalsIgnoreCase("/rc"+deviceID)){
                assertTrue("returned device not equal to original one",isRetrivedInstanceIdentical(d));
                break;
            }
        }



    }

    private boolean isRetrivedInstanceIdentical(LSLCDevice retrievedDevice){

        // compare simple values

        // TODO // device id changes due action of RC , BUG . equality test disabled !
        //if(!retrievedDevice.id.equalsIgnoreCase(device.id)) return false;
        assertEquals(device.name,retrievedDevice.name);
        assertEquals(device.description,retrievedDevice.description);
        assertEquals(device.ttl,retrievedDevice.ttl);

        // compare resources of device
        for(int i=0; i < retrievedDevice.resources.size(); i++) {
            // compare simple types
            // TODO // device id changes due action of RC , BUG . equality test disabled !
            //assertEquals(retrievedDevice.resources.get(i).id,device.resources.get(i).id);
//            if(!retrievedDevice.resources.get(i).id.equalsIgnoreCase(device.resources.get(i).id)) return false;
            assertEquals(retrievedDevice.resources.get(i).name, device.resources.get(i).name);
//            if(!retrievedDevice.resources.get(i).name.equalsIgnoreCase(device.resources.get(i).name)) return false;

            // compare representation
            LSLCRepresentation retrievedRepresentation = retrievedDevice.resources.get(i).representation;
            assertEquals(retrievedRepresentation.type,device.resources.get(i).representation.type);
            assertEquals(retrievedRepresentation.contentType.toString(),device.resources.get(i).representation.contentType.toString());

            // compare protocols
            List<LSLCProtocol> retrievedProtocols = retrievedDevice.resources.get(i).protocols;
            for(int j=0; j < retrievedProtocols.size();j++){
                LSLCProtocol retrievedProtocol = retrievedProtocols.get(j);
                // simple types
                assertEquals(retrievedProtocol.type,device.resources.get(i).protocols.get(j).type);
                assertEquals(retrievedProtocol.endpoint.toString(),device.resources.get(i).protocols.get(j).endpoint.toString());
                // protocol methods
                List<String> retrievedMethods = retrievedProtocol.methods;
                for(int z=0; z < retrievedMethods.size();z++){
                    assertEquals(retrievedMethods.get(z),device.resources.get(i).protocols.get(j).methods.get(z));
                }
                // content-types
                List<ContentType> retrievedContentTypes = retrievedProtocol.contentTypes;
                for(int z=0; z < retrievedContentTypes.size();z++){
                    assertEquals(retrievedContentTypes.get(z).toString(),device.resources.get(i).protocols.get(j).contentTypes.get(z).toString());
                }
            }


        }

        return true;
    }



}
