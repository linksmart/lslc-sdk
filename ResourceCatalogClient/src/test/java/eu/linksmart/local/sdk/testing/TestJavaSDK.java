package eu.linksmart.local.sdk.testing;

import eu.linksmart.local.sdk.*;
import eu.linksmart.local.sdk.clients.DeviceManagementClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

/**
 * Created by carlos on 27.10.14.
 */
public class TestJavaSDK {

    public String rawResource ="{\"id\":\"/rc/Leshy/TESTING-FINDDEVICE/46237b21-4737-491a-964a-efd0ed4157d2\",\"type\":\"Resource\",\"name\":\"Resource A\",\"meta\":null,\"protocols\":[{\"type\":\"REST\",\"endpoint\":{\"another-void-alltypes-possible-broker-from-hell\":\"???:::/SH-G/¿X\",\"fancy-alien-broker-from-hell\":\"320uff0:::////30402045r???\"},\"methods\":[\"GET\"],\"content-types\":[\"text/plain\"]}],\"representation\":{\"text/plain\":{\"type\":\"number\"}},\"device\":\"/rc/Leshy/TESTING-FINDDEVICE\"}";
    public String rawDevice   ="{\"id\":\"Leshy/TESTING-PARSE\",\"type\":\"Device\",\"name\":\"DeviceA\",\"meta\":null,\"description\":\"Device registered from Java DeviceManagementClient\",\"ttl\":60,\"created\":\"2014-11-03T14:35:44.593930162+01:00\",\"updated\":\"2014-11-03T14:35:44.593930162+01:00\",\"expires\":\"2014-11-03T14:36:44.593930162+01:00\",\"resources\":[{\"id\":\"/rc/Leshy/TESTING-GETDEVICE/97f1e876-dee0-49e7-8d07-fdafb713c9e4\",\"type\":\"Resource\",\"name\":\"Resource A\",\"meta\":null,\"protocols\":[{\"type\":\"REST\",\"endpoint\":{\"another-void-alltypes-possible-broker-from-hell\":\"???:::/SH-G/¿X\",\"fancy-alien-broker-from-hell\":\"320uff0:::////30402045r???\"},\"methods\":[\"GET\"],\"content-types\":[\"text/plain\"]}],\"representation\":{\"text/plain\":{\"type\":\"number\"}},\"device\":\"/rc/Leshy/TESTING-GETDEVICE\"}],\"page\":1,\"per_page\":100,\"total\":1}";

    public LSLCDevice device;
    DeviceManagementClient client;

    @Before
    public void setupDeviceRegistrationDocument() throws MalformedURLException, ParseException {

        //client = new DeviceManagementClient(new URL("http://localhost:7778/rc"));
        client = new DeviceManagementClient(new URL("http://gando.fit.fraunhofer.de:8091/rc"));


        ContentType aContentType = new ContentType("text/plain");

        device = new LSLCDevice();
        device.name = new String("DeviceA");  // <-- will be overwritten in given tests
        device.description = new String("Device registered from Java DeviceManagementClient");
        device.ttl = 60;

        LSLCProtocol protocol = new LSLCProtocol();
        LSLCRepresentation representation = new LSLCRepresentation();
        LSLCResource resource = new LSLCResource();

        representation.contentType = aContentType;
        representation.type = new String("number");

        protocol.endpoint.put("fancy-alien-broker-from-hell", "320uff0:::////30402045r???");
        protocol.endpoint.put("another-void-alltypes-possible-broker-from-hell","???:::/SH-G/¿X");
        protocol.type = new String("REST");
        protocol.methods.add("GET");
        protocol.contentTypes.add(new ContentType("text/plain"));

        resource.name = new String("Resource A");
        resource.representation = representation;
        resource.protocols.add(protocol);

        device.resources.add(resource);


    }
    @Test
    public void parseDevice() throws ParseException {


        // offline device parsing test
        // tests parsing of a JSON device raw string
        LSLCDevice parsedDevice = client.parseDevice(rawDevice);
        device.id="Leshy/TESTING-PARSE";
        assertTrue("parsed not equals the original", isRetrivedInstanceIdentical(parsedDevice,device.id));
    }
    @Ignore
    public void parseResource() throws ParseException {
        LSLCResource parsedResource = client.parseResource(rawResource);
        assertEquals(device.resources.get(0).name,parsedResource.name);
    }

    @Test
    public void findDevice() throws MalformedURLException, InterruptedException {


        device.name = "TESTING-FINDDEVICE";
        String regID = client.add(device);
        System.out.println("[INTEGRATION] registered device id: "+regID);

        LSLCDevice FromRC = client.findDevice("name", RCOperation.CONTAINS, device.name);
        client.delete(regID);

        // test if all fields were retrieved properly
        assertTrue("returned device not equal to original one", isRetrivedInstanceIdentical(FromRC,regID));
        System.out.println("[INTEGRATION] device removed");
        System.out.println("[INTEGRATION] ****************************************");

    }

    @Test
    public void findResource() throws MalformedURLException, InterruptedException {


        device.name = "TESTING-FINDRESOURCE";
        String regID = client.add(device);
        System.out.println("[INTEGRATION] registered device id: "+regID);

        LSLCResource FromRC = client.findResource("name", RCOperation.CONTAINS, device.resources.get(0).name);
        //System.out.println("[INTEGRATION] device id: "+deviceFromRC.id);
        client.delete(regID);

        // attach parsed resource to previous device for comparision, since comparision method works only on device instances
        LSLCDevice deviceCopy = device;
        deviceCopy.id = regID;
        // here the retrieved resource is injected into a device
        deviceCopy.resources.set(0,FromRC);

        // test if all fields were retrieved properly
        assertTrue("returned device not equal to original one", isRetrivedInstanceIdentical(deviceCopy,regID));
        System.out.println("[INTEGRATION] device removed");
        System.out.println("[INTEGRATION] ****************************************");

    }
    @Test
    public void findResources() throws MalformedURLException, InterruptedException {


        device.name = "TESTING-FINDRESOURCES";
        String regID = client.add(device);
        System.out.println("[INTEGRATION] registered device id: "+regID);

        List<LSLCResource> FromRC = client.findResources("name", RCOperation.CONTAINS, device.resources.get(0).name, 1, 100);
        client.delete(regID);
        System.out.println("[INTEGRATION] device removed");

        // compare a few values form retrieved resources
        assertEquals(device.resources.get(0).name,FromRC.get(0).name);
        assertEquals(device.resources.get(0).representation.contentType.toString(),FromRC.get(0).representation.contentType.toString());
        assertEquals(device.resources.get(0).protocols.get(0).methods.get(0),FromRC.get(0).protocols.get(0).methods.get(0));
        assertEquals(device.resources.get(0).protocols.get(0).contentTypes.get(0).toString(), FromRC.get(0).protocols.get(0).contentTypes.get(0).toString());
        assertEquals(device.resources.get(0).protocols.get(0).endpoint.get("fancy-alien-broker-from-hell"),FromRC.get(0).protocols.get(0).endpoint.get("fancy-alien-broker-from-hell"));
        assertEquals(device.resources.get(0).protocols.get(0).type,FromRC.get(0).protocols.get(0).type);



        System.out.println("[INTEGRATION] ****************************************");

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
        System.out.println("[INTEGRATION] device removed");
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
        System.out.println("[INTEGRATION] device removed");
        System.out.println("[INTEGRATION] ****************************************");






    }
    @Test
    public void getDevices() throws MalformedURLException {


        device.name = "TESTING-GETDEVICES";
        String regID = client.add(device);
        System.out.println("[INTEGRATION] registered device id: "+regID);

        List<LSLCDevice> devices = client.getDevices(1,100);

        client.delete(regID);
        System.out.println("[INTEGRATION] device removed");

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
    @Test
    public void findDevices(){
        device.name = "TESTING-FINDDEVICES";
        String regID = client.add(device);
        System.out.println("[INTEGRATION] registered device id: "+regID);

        List<LSLCDevice> devices = client.findDevices("name", RCOperation.CONTAINS, device.name, 1, 100);

        client.delete(regID);
        System.out.println("[INTEGRATION] device removed");

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

        System.out.println("[INTEGRATION] comparing devices...");
        assertEquals(expectedID,retrievedDevice.id);
        assertEquals(device.name,retrievedDevice.name);
        assertEquals(device.description,retrievedDevice.description);
        assertEquals(device.ttl,retrievedDevice.ttl);
        System.out.println("[INTEGRATION] comparing devices OK");

        // compare resources of device
        System.out.println("[INTEGRATION] comparing "+retrievedDevice.resources.size()+" resources...");
        for(int i=0; i < retrievedDevice.resources.size(); i++) {
            // compare simple types
            assertEquals(retrievedDevice.resources.get(i).name, device.resources.get(i).name);

            // compare representation
            LSLCRepresentation retrievedRepresentation = retrievedDevice.resources.get(i).representation;
            assertEquals(retrievedRepresentation.type, device.resources.get(i).representation.type);
            assertEquals(retrievedRepresentation.contentType.toString(),device.resources.get(i).representation.contentType.toString());

            System.out.println("  [INTEGRATION] comparing protocols...");
            // compare protocols
            List<LSLCProtocol> retrievedProtocols = retrievedDevice.resources.get(i).protocols;
            System.out.println("  [INTEGRATION] number of protocols: "+retrievedDevice.resources.get(i).protocols.size());
            for(int j=0; j < retrievedProtocols.size();j++){
                System.out.println("  [INTEGRATION] protocol #"+j);
                LSLCProtocol retrievedProtocol = retrievedProtocols.get(j);
                // simple types
                assertEquals(retrievedProtocol.type,device.resources.get(i).protocols.get(j).type);

                // compare alien protocol endpoints
                System.out.println("    [INTEGRATION] comparing alien endpoints...");
                HashMap<String, String> alienEndpoints = retrievedProtocol.endpoint;
                System.out.println("      [INTEGRATION] alien endpoints size: "+alienEndpoints.size());
                HashMap<String, String> originalEndpoints = device.resources.get(i).protocols.get(j).endpoint;
                System.out.println("      [INTEGRATION] original endpoints size: "+originalEndpoints.size());
                for(Map.Entry<String, String> entry : alienEndpoints.entrySet()) {
                    System.out.println("      [INTEGRATION] fancy alien broker endpoint >> "+entry.getKey()+": "+entry.getValue());
                    assertEquals(originalEndpoints.get(entry.getKey()), entry.getValue());
                }
                System.out.println("    [INTEGRATION] alien endpoints OK");
                assertEquals(retrievedProtocol.endpoint.toString(), device.resources.get(i).protocols.get(j).endpoint.toString());
                // protocol methods
                System.out.println("    [INTEGRATION] comparing methods...");
                List<String> retrievedMethods = retrievedProtocol.methods;
                for(int z=0; z < retrievedMethods.size();z++){
                    assertEquals(retrievedMethods.get(z),device.resources.get(i).protocols.get(j).methods.get(z));
                }
                System.out.println("    [INTEGRATION] methods OK");
                // content-types
                List<ContentType> retrievedContentTypes = retrievedProtocol.contentTypes;
                for(int z=0; z < retrievedContentTypes.size();z++){
                    assertEquals(retrievedContentTypes.get(z).toString(),device.resources.get(i).protocols.get(j).contentTypes.get(z).toString());
                }
            }
            System.out.println("  [INTEGRATION] protocols OK");


        }
        System.out.println("[INTEGRATION] comparing resources OK");

        return true;
    }



}
