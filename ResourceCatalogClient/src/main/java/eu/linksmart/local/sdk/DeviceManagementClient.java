package eu.linksmart.local.sdk;

import com.google.gson.*;

import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by carlos on 24.10.14.
 */
public class DeviceManagementClient implements IDeviceManagement {

    private String hostID;
    private URL ResourceCatalogEndpoint;

    public DeviceManagementClient(URL ResourceCatalog){
        this.ResourceCatalogEndpoint = ResourceCatalog;
        try {
            hostID = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean registerDevice(LSLCDevice aDevice) {

//        String hostID = "JavaRegistrationClient";

        int totalResources = aDevice.resources.size();

        // create root registration document
        JsonObject aRegistration = new JsonObject();
        aRegistration.addProperty("id",hostID+"/"+aDevice.name);
        aRegistration.addProperty("type",aDevice.type);
        aRegistration.addProperty("name",aDevice.name);
        aRegistration.addProperty("description",aDevice.description);
        aRegistration.addProperty("ttl",aDevice.ttl);

        // create empty array of  resources
        JsonArray resourcesArray = new JsonArray();


        // iterate over all resources from device

        for(int i=0; i < totalResources; i++) {
            // create root resource document
            JsonObject aResource = new JsonObject();
            // add simple types to JSON resource representation
            aResource.addProperty("id", hostID + "/" + aDevice.name + "/" + UUID.randomUUID().toString());
            aResource.addProperty("type", aDevice.resources.get(i).type);
            aResource.addProperty("name", aDevice.resources.get(i).name);
            aResource.addProperty("device", aDevice.resources.get(i).device);


            // create array of protocols
            JsonArray resourceProtocolArray = new JsonArray();
            // interate over all protocols from device
            for(int j=0; j < aDevice.resources.get(i).protocols.size(); j++ ) {
                // create one protocol item
                JsonObject protocol = new JsonObject();
                protocol.addProperty("type", aDevice.resources.get(i).protocols.get(j).type);

                JsonObject endpoint = new JsonObject();
                endpoint.addProperty("url", aDevice.resources.get(i).protocols.get(j).endpoint.toString());

                protocol.add("endpoint", endpoint);

                JsonArray methods = new JsonArray();
                // interate over all possible protocol methods
                for(int z=0; z < aDevice.resources.get(i).protocols.get(j).methods.size(); z++) {
                    JsonObject method = new JsonObject();
                    JsonPrimitive item = new JsonPrimitive(aDevice.resources.get(i).protocols.get(j).methods.get(z));
                    methods.add(item);
                    protocol.add("methods", methods);
                }


                JsonArray contentTypes = new JsonArray();
                // iterate over all possible content types of given protocol
                for(int z=0; z < aDevice.resources.get(i).protocols.get(j).contentTypes.size(); z++ ) {
                    System.out.println("content type: " + aDevice.resources.get(i).protocols.get(j).contentTypes.get(0).toString());
                    JsonPrimitive contentType = new JsonPrimitive(aDevice.resources.get(i).protocols.get(j).contentTypes.get(0).toString());
                    contentTypes.add(contentType);
                    protocol.add("content-types", contentTypes);
                }

                // add protocol to protocol array
                resourceProtocolArray.add(protocol);
                // add protocol array to root document
                aResource.add("protocols", resourceProtocolArray);
            }

            // create representation document . WTF quite complex type with double layer. SIMPLIFY !
            JsonObject representation = new JsonObject();
            JsonObject textPlain = new JsonObject();
            textPlain.addProperty("type", aDevice.resources.get(i).representation.type);
            representation.add(aDevice.resources.get(i).representation.contentType.toString(), textPlain);
            aResource.add("representation", representation);


            resourcesArray.add(aResource);
        }

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
            //System.out.println("from server: "+jsonString);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    public boolean removeDevice(String deviceID) {

        String deleteEndpoint = ResourceCatalogEndpoint.toString()+deviceID;
        // try to register device inside ResourceCatalog
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(deleteEndpoint).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();

//            System.out.println("code: "+responseCode);
//            System.out.println("message: "+connection.getResponseMessage());

            connection.disconnect();
            if(responseCode==200) return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public LSLCDevice getDevice(String deviceID) {
        String getEndpoint = ResourceCatalogEndpoint.toString()+deviceID;
        // try to retrieve device from ResourceCatalog
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(getEndpoint).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            //connection.setRequestProperty("Accept", "application/ld+json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            //OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            //writer.write(payload);
            //writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer jsonString = new StringBuffer();
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
            String deviceString = jsonString.toString();
            System.out.println("response message: "+connection.getResponseMessage());
            System.out.println("response code: "+connection.getResponseCode());
            System.out.println("from server: >>>"+deviceString+"<<<");
            JsonParser parser = new JsonParser();
            JsonElement parsedElement = parser.parse(deviceString);
            System.out.println("json element: "+parsedElement);

            JsonObject deviceJSON = parsedElement.getAsJsonObject();
            System.out.println("json object: "+deviceJSON);

            LSLCDevice aDevice = new LSLCDevice();

            // read simple types
            aDevice.name = String.valueOf(deviceJSON.get("name"));
            aDevice.ttl = Integer.valueOf(String.valueOf(deviceJSON.get("ttl")));
            aDevice.description = String.valueOf(deviceJSON.get("description"));
            aDevice.id = String.valueOf(deviceJSON.get("id"));

            // read resources from array
            JsonArray resourcesJSON = deviceJSON.getAsJsonArray("resources");
            for(int i=0; i < resourcesJSON.size();i++){
                JsonObject resourceJSON = resourcesJSON.get(i).getAsJsonObject();
                LSLCResource aResource = new LSLCResource();
                // read simple types
                aResource.name = String.valueOf(resourceJSON.get("name"));
                aResource.device = String.valueOf(resourceJSON.get("device"));
                aResource.id = String.valueOf(resourceJSON.get("id"));
                // read protocols from array
                JsonArray protocolsJSON = resourceJSON.getAsJsonArray("protocols");
                for(int j=0; j < protocolsJSON.size(); j++){
                    JsonObject protocolJSON = protocolsJSON.get(0).getAsJsonObject();
                    LSLCProtocol aProtocol = new LSLCProtocol();
                    // read simple types
                    JsonObject urlJSON = protocolJSON.get("endpoint").getAsJsonObject();
                    aProtocol.endpoint = new URL(urlJSON.get("url").getAsString());
                    aProtocol.type = String.valueOf(protocolJSON.get("type"));
                    // read methods from array
                    JsonArray methodsJSON = protocolJSON.getAsJsonArray("methods");
                    for(int z=0; z < methodsJSON.size(); z++){
                        JsonPrimitive methodJSON = methodsJSON.get(z).getAsJsonPrimitive();
                        aProtocol.methods.add(methodJSON.getAsString());
                    }
                    // read content-types from array
                    JsonArray contentTypesJSON = protocolJSON.getAsJsonArray("content-types");
                    for(int z=0; z < contentTypesJSON.size(); z++){
                        JsonPrimitive contentTypeJSON = contentTypesJSON.get(z).getAsJsonPrimitive();
                        aProtocol.contentTypes.add(new ContentType(contentTypeJSON.getAsString()));
                    }


                }


                aDevice.resources.add(aResource);
            }


            System.out.println("device name: "+aDevice.name);
            System.out.println("device ttl: "+aDevice.ttl);


            //LSLCDevice device = new Gson().fromJson(deviceString, LSLCDevice.class);
            //System.out.println("descritpion: "+device.description);
            //System.out.println(device.id);
            //System.out.println(device.name);
            //System.out.println(device.ttl);
            //System.out.println(device.type);
            connection.disconnect();

            return aDevice;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <List>ArrayList<LSLCDevice> getAllDevices() {

        String getAllDevicesEndpoint = ResourceCatalogEndpoint.toString();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(getAllDevicesEndpoint).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            //connection.setRequestProperty("Accept", "application/ld+json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            //OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            //writer.write(payload);
            //writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer jsonString = new StringBuffer();
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
            String deviceString = jsonString.toString();
            System.out.println("response message: " + connection.getResponseMessage());
            System.out.println("response code: " + connection.getResponseCode());
            System.out.println("from server: >>>" + deviceString + "<<<");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<LSLCDevice> ret = new ArrayList<LSLCDevice>();

        return ret;
    }
}
