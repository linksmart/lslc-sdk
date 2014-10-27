package eu.linksmart.local.sdk;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * Created by carlos on 24.10.14.
 */
public class DeviceManagementClient implements IDeviceManagement {

    private URL ResourceCatalogEndpoint;

    public DeviceManagementClient(URL ResourceCatalog){
        this.ResourceCatalogEndpoint = ResourceCatalog;
    }


    @Override
    public boolean registerDevice(LSLCDevice aDevice) {

        String hostID = "JavaRegistrationClient";

        try {
            hostID = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // create root registration document
        JsonObject aRegistration = new JsonObject();
        aRegistration.addProperty("id",hostID+"/"+aDevice.name);
        aRegistration.addProperty("type",aDevice.type);
        aRegistration.addProperty("name",aDevice.name);
        aRegistration.addProperty("description",aDevice.description);
        aRegistration.addProperty("ttl",aDevice.ttl);


        // create root resource document
        JsonObject aResource = new JsonObject();
        // add simple types
        aResource.addProperty("id", hostID+"/"+aDevice.name+"/"+ UUID.randomUUID().toString());
        aResource.addProperty("type",aDevice.resources.get(0).type);
        aResource.addProperty("name",aDevice.resources.get(0).name);
        aResource.addProperty("device",aDevice.resources.get(0).device);


        // create array of protocols
        JsonArray resourceProtocolArray = new JsonArray();
        // create one protocol item
        JsonObject protocol = new JsonObject();
        protocol.addProperty("type",aDevice.resources.get(0).protocols.get(0).type);

        JsonObject endpoint = new JsonObject();
        endpoint.addProperty("url",aDevice.resources.get(0).protocols.get(0).endpoint.toString());

        protocol.add("endpoint", endpoint);

        JsonArray methods = new JsonArray();
        JsonObject method = new JsonObject();

        JsonPrimitive item = new JsonPrimitive(aDevice.resources.get(0).protocols.get(0).methods.get(0));
        methods.add(item);
        protocol.add("methods",methods);


        JsonArray contentTypes = new JsonArray();
        System.out.println("content type: "+aDevice.resources.get(0).protocols.get(0).contentTypes.get(0).toString());
        JsonPrimitive contentType = new JsonPrimitive(aDevice.resources.get(0).protocols.get(0).contentTypes.get(0).toString());
        contentTypes.add(contentType);
        protocol.add("content-types",contentTypes);

        // add protocol to protocol array
        resourceProtocolArray.add(protocol);
        // add protocol array to root document
        aResource.add("protocols",resourceProtocolArray);

        // create representation document . WTF quite complex type with double layer. SIMPLIFY !
        JsonObject representation = new JsonObject();
        JsonObject textPlain = new JsonObject();
        textPlain.addProperty("type",aDevice.resources.get(0).representation.type);
        representation.add(aDevice.resources.get(0).representation.contentType.toString(), textPlain);
        aResource.add("representation",representation);


        // create array of  resources
        JsonArray resourcesArray = new JsonArray();
        resourcesArray.add(aResource);

        aRegistration.add("resources",resourcesArray);


        String payload = aRegistration.toString();

        System.out.println("payload: "+aRegistration);

        // try to register device inside ResourceCatalog
        try {
            HttpURLConnection connection = (HttpURLConnection) ResourceCatalogEndpoint.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            //connection.setRequestProperty("Accept", "application/ld+json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer jsonString = new StringBuffer();
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
            connection.disconnect();
            System.out.println("from server: "+jsonString);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    public boolean removeDevice(String deviceID) {
        return false;
    }

    @Override
    public LSLCDevice getDevice(String deviceID) {
        return null;
    }
}