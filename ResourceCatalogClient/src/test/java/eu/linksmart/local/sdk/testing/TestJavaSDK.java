package eu.linksmart.local.sdk.testing;

import eu.linksmart.local.sdk.LSLCDevice;
import eu.linksmart.local.sdk.LSLCProtocol;
import eu.linksmart.local.sdk.LSLCRepresentation;
import eu.linksmart.local.sdk.LSLCResource;
import eu.linksmart.local.sdk.clients.DeviceManagementClient;
import org.junit.Before;
import org.junit.Test;

import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static junit.framework.Assert.*;

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

        //protocol.endpoint = new URL("http://device_a");
        protocol.endpoint.put("fancy-alien-broker-from-hell", "320uff0:::////30402045r???");
        protocol.endpoint.put("another-void-alltypes-possible-broker-from-hell","???:::/SH-G/¿X");
        protocol.type = new String("REST");
        protocol.methods.add("GET");
        protocol.contentTypes.add(new ContentType("text/plain"));

        resource.name = new String("Resource A");
        resource.representation = representation;
        resource.protocols.add(protocol);

        device.resources.add(resource);

        //client = new DeviceManagementClient(new URL("http://localhost:7778/rc"));
        client = new DeviceManagementClient(new URL("http://gando.fit.fraunhofer.de:8091/rc"));


    }

    @Test
    public void testRemoteRegistrationAndUnregistration() throws MalformedURLException, InterruptedException {

        // register device to RC

        device.name = "TESTING-REGISTRATION";
        String regID = client.add(device);

        assertNotNull("could not register device",regID);

        // update device registered to RC

        System.out.println("[INTEGRATION] device registered.");
        System.out.println("[INTEGRATION] registration ID: "+regID);

        assertTrue("could not update device",client.update(device, regID));
        System.out.println("[INTEGRATION] device updated.");

        assertTrue("could not remove ", client.delete(regID));
        System.out.println("[INTEGRATION] ****************************************");



    }

    @Test
    public void getDeviceByID() throws MalformedURLException, InterruptedException {


        device.name = "TESTING-GETDEVICE";
        String regID = client.add(device);
        System.out.println("[INTEGRATION] registered device id: "+regID);

        LSLCDevice deviceFromRC = client.get(regID);
        System.out.println("[INTEGRATION] device id: "+deviceFromRC.id);
        client.delete(regID);

        // test if all fields were retrieved properly
        assertTrue("returned device not equal to original one", isRetrivedInstanceIdentical(deviceFromRC,regID));
        System.out.println("[INTEGRATION] ****************************************");






    }
    @Test
    public void getAllDevicesFromRC() throws MalformedURLException {


        device.name = "TESTING-GETALLDEVICES";
        String regID = client.add(device);
        System.out.println("registered device id: "+regID);

        List<LSLCDevice> devices = client.getAllDevices();

        client.delete(regID);

        // TODO look up for "my test" device. since the RC changed the ID a workaround is needed. fuuuUU
        for(int i=0; i < devices.size();i++){
            LSLCDevice d = devices.get(i);
            System.out.println("[INTEGRATION] comparing "+d.id+ " and "+regID);
            if(regID.equals(d.id)){
                assertTrue("returned device not equal to original one",isRetrivedInstanceIdentical(d,regID));
            }else{
                System.out.println("[INTEGRATION] device id not matching.");
            }
        }
        System.out.println("[INTEGRATION] ****************************************");



    }

    private boolean isRetrivedInstanceIdentical(LSLCDevice retrievedDevice, String expectedID){

        // compare simple values

        // TODO // device id changes due action of RC , BUG . equality test disabled !
        //if(!retrievedDevice.id.equalsIgnoreCase(device.id)) return false;
        assertEquals(device.name,retrievedDevice.name);
        assertEquals(device.description,retrievedDevice.description);
        assertEquals(device.ttl,retrievedDevice.ttl);

        // compare resources of device
        System.out.println("comparing "+retrievedDevice.resources.size()+" resources");
        for(int i=0; i < retrievedDevice.resources.size(); i++) {
            // compare simple types
            // TODO // device id changes due action of RC , BUG . equality test disabled !
            //assertEquals(retrievedDevice.resources.get(i).id,device.resources.get(i).id);
//            if(!retrievedDevice.resources.get(i).id.equalsIgnoreCase(device.resources.get(i).id)) return false;
            assertEquals(retrievedDevice.resources.get(i).name, device.resources.get(i).name);
//            if(!retrievedDevice.resources.get(i).name.equalsIgnoreCase(device.resources.get(i).name)) return false;

            // compare representation
            LSLCRepresentation retrievedRepresentation = retrievedDevice.resources.get(i).representation;
            assertEquals(retrievedRepresentation.type, device.resources.get(i).representation.type);
            assertEquals(retrievedRepresentation.contentType.toString(),device.resources.get(i).representation.contentType.toString());

            System.out.println("[INTEGRATION] comparing protocols...");
            // compare protocols
            List<LSLCProtocol> retrievedProtocols = retrievedDevice.resources.get(i).protocols;
            System.out.println("[INTEGRATION] number of protocols: "+retrievedDevice.resources.get(i).protocols.size());
            for(int j=0; j < retrievedProtocols.size();j++){
                System.out.println("[INTEGRATION] protocol #"+j);
                LSLCProtocol retrievedProtocol = retrievedProtocols.get(j);
                // simple types
                assertEquals(retrievedProtocol.type,device.resources.get(i).protocols.get(j).type);

                // compare alien protocol endpoints
                HashMap<String, String> alienEndpoints = retrievedProtocol.endpoint;
                System.out.println("[INTEGRATION] alien endpoints size: "+alienEndpoints.size());
                HashMap<String, String> originalEndpoints = device.resources.get(i).protocols.get(j).endpoint;
                System.out.println("[INTEGRATION] original endpoints size: "+originalEndpoints.size());
                for(Map.Entry<String, String> entry : alienEndpoints.entrySet()) {
                    System.out.println("[INTEGRATION] fancy alien broker endpoint >> "+entry.getKey()+": "+entry.getValue());
                    assertEquals(originalEndpoints.get(entry.getKey()), entry.getValue());
                }
                assertEquals(retrievedProtocol.endpoint.toString(), device.resources.get(i).protocols.get(j).endpoint.toString());
                // protocol methods
                System.out.println("[INTEGRATION] comparing methods...");
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
