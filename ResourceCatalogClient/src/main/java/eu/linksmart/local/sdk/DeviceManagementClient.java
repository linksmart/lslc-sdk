package eu.linksmart.local.sdk;

import com.google.gson.*;

import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.UUID;
=======
import java.util.*;
>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1

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
            //aResource.addProperty("device", aDevice.resources.get(i).device);


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
                    //System.out.println("content type: " + aDevice.resources.get(i).protocols.get(j).contentTypes.get(0).toString());
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

        //System.out.println("payload: "+aRegistration);

        // try to register device inside ResourceCatalog
        String registerEndpoint = ResourceCatalogEndpoint+"/";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(registerEndpoint).openConnection();
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

<<<<<<< HEAD
        String deleteEndpoint = ResourceCatalogEndpoint.toString()+deviceID;
=======
        String deleteEndpoint = ResourceCatalogEndpoint.toString()+"/"+deviceID;
>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1
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
<<<<<<< HEAD
        String getEndpoint = ResourceCatalogEndpoint.toString()+deviceID;
=======
        String getEndpoint = ResourceCatalogEndpoint.toString()+"/"+deviceID;
>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1
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
<<<<<<< HEAD
            System.out.println("response message: "+connection.getResponseMessage());
            System.out.println("response code: "+connection.getResponseCode());
            System.out.println("from server: >>>"+deviceString+"<<<");
            JsonParser parser = new JsonParser();
            JsonElement parsedElement = parser.parse(deviceString);
            System.out.println("json element: "+parsedElement);

            JsonObject deviceJSON = parsedElement.getAsJsonObject();
            System.out.println("json object: "+deviceJSON);
=======
            //System.out.println("response message: "+connection.getResponseMessage());
            //System.out.println("response code: "+connection.getResponseCode());
            //System.out.println("from server: >>>"+deviceString+"<<<");
            JsonParser parser = new JsonParser();
            JsonElement parsedElement = parser.parse(deviceString);
            //System.out.println("json element: "+parsedElement);

            JsonObject deviceJSON = parsedElement.getAsJsonObject();
            //System.out.println("json object: "+deviceJSON);
>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1

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
<<<<<<< HEAD
                    JsonObject protocolJSON = protocolsJSON.get(0).getAsJsonObject();
=======
                    JsonObject protocolJSON = protocolsJSON.get(j).getAsJsonObject();
>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1
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


<<<<<<< HEAD
            System.out.println("device name: "+aDevice.name);
            System.out.println("device ttl: "+aDevice.ttl);
=======
            //System.out.println("device name: "+aDevice.name);
            //System.out.println("device ttl: "+aDevice.ttl);
>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1


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
<<<<<<< HEAD
    public <List>ArrayList<LSLCDevice> getAllDevices() {

        String getAllDevicesEndpoint = ResourceCatalogEndpoint.toString();
        try {
=======
    public List<LSLCDevice> getAllDevices() {
        String getAllDevicesEndpoint = ResourceCatalogEndpoint.toString();
        try {

            // IO section starts
>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1
            HttpURLConnection connection = (HttpURLConnection) new URL(getAllDevicesEndpoint).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
<<<<<<< HEAD
            //connection.setRequestProperty("Accept", "application/ld+json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            //OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            //writer.write(payload);
            //writer.close();
=======
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer jsonString = new StringBuffer();
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
<<<<<<< HEAD
            String deviceString = jsonString.toString();
            System.out.println("response message: " + connection.getResponseMessage());
            System.out.println("response code: " + connection.getResponseCode());
            System.out.println("from server: >>>" + deviceString + "<<<");
=======
            // IO section ends

            // parsing section starts
            String deviceString = jsonString.toString();
            //System.out.println("response message: " + connection.getResponseMessage());
            //System.out.println("response code: " + connection.getResponseCode());
            //System.out.println("from server: >>>" + deviceString + "<<<");

            JsonParser parser = new JsonParser();
            JsonElement parsedElement = parser.parse(deviceString);
            //System.out.println("json rooot element: "+parsedElement);


            JsonObject devicesJSON = parsedElement.getAsJsonObject().getAsJsonObject("devices");

            //System.out.println("json objects: "+devicesJSON);
            Set<Map.Entry<String, JsonElement>> setOfObjects = devicesJSON.entrySet();

            // device parsing
            //ArrayList<LSLCDevice> devices = new ArrayList<LSLCDevice>();
            Hashtable<String,LSLCDevice> devices = new Hashtable<String, LSLCDevice>();
            // retrieve all devices from objects stored as properties in JSON document. Why no array here like in other structures ???.
            // Because we love to complicate parsing routines
            for(Map.Entry<String, JsonElement> entry : setOfObjects){
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                JsonObject deviceJSON  = value.getAsJsonObject();
                //System.out.println("single device: "+deviceJSON);
                // remove empty id bullshit, like rc "device"
                if(!deviceJSON.get("id").toString().equalsIgnoreCase("")){
                    LSLCDevice device = new LSLCDevice();
                    // add simple types
                    device.id = deviceJSON.get("id").getAsString();
                    device.name = deviceJSON.get("name").getAsString();
                    device.ttl = Integer.valueOf(deviceJSON.get("ttl").getAsString());
                    device.description = deviceJSON.get("description").getAsString();
                    //System.out.println("adding device to hash map (id): "+device.id);
                    devices.put(device.id,device);
                }

            }

            // resource array parsing and copying
            JsonArray resourcesJSON = parsedElement.getAsJsonObject().getAsJsonArray("resources");
            //System.out.println("json resources : "+resourcesJSON);
            // iterate over all resources to find matching device
            for(int i=0; i < resourcesJSON.size(); i++){
                JsonObject resourceJSON = resourcesJSON.get(i).getAsJsonObject();
                // workaround for RC bug
                String resourceID = resourceJSON.get("id").getAsString();
                if(resourceID.equalsIgnoreCase("/rc/")){
                    //      System.out.println("ignoring fake resource :"+i);
                }else{
                    // resource found, creating representation class
                    LSLCResource newResource = new LSLCResource();
                    // copy simple types
                    newResource.id = resourceID;
                    newResource.device = resourceJSON.get("device").getAsString();
                    newResource.name = resourceJSON.get("name").getAsString();

                    // representation retrieval
                    LSLCRepresentation newRepresentation = new LSLCRepresentation();
                    JsonObject representationJSON = resourceJSON.get("representation").getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> setOfContentTypes = representationJSON.entrySet();
                    // iterate over possible content types
                    for(Map.Entry<String, JsonElement> entry : setOfContentTypes) {
                        String key = entry.getKey();
                        JsonElement value = entry.getValue();
                        //System.out.println("content-type Key  : "+key);
                        //System.out.println("content-type value: "+value);

                        newRepresentation.contentType = new ContentType(key);
                        newRepresentation.type = value.getAsJsonObject().get("type").getAsString();
                    }
                    // add resource representation to resource instance
                    newResource.representation = newRepresentation;

                    // protocols retrieval
                    JsonArray protocolsJSON = resourceJSON.get("protocols").getAsJsonArray();
                    LSLCProtocol newProtocol = new LSLCProtocol();
                    for(int j=0; j < protocolsJSON.size();j++){
                        JsonObject protocolJSON = protocolsJSON.get(j).getAsJsonObject();

                        // copy simple types
                        newProtocol.type = protocolJSON.get("type").getAsString();
                        JsonObject urlJSON = protocolJSON.get("endpoint").getAsJsonObject();
    //                        System.out.println("url: "+urlJSON);
                        newProtocol.endpoint = new URL(urlJSON.get("url").getAsString());

                        // copy protocol methods
                        JsonArray methodsJSON = protocolJSON.getAsJsonArray("methods");
                        for(int z=0; z < methodsJSON.size();z++) {
                            newProtocol.methods.add(methodsJSON.get(z).getAsJsonPrimitive().getAsString());
                        }

                        // copy content-types
                        JsonArray contentTypesJSON = protocolJSON.getAsJsonArray("content-types");
                        for(int z=0; z < contentTypesJSON.size();z++){
                            ContentType newContentType = new ContentType(contentTypesJSON.get(z).getAsString());
                            newProtocol.contentTypes.add(newContentType);
                        }


                    }
                    // add new protocol to resource instance
                    newResource.protocols.add(newProtocol);

                    // add whole resource to device hash map using device id as key
                    //System.out.println("using key(id): "+newResource.device);
                    LSLCDevice d    = devices.get(newResource.device);
                    //System.out.println("using device with id: "+d.id);
                    devices.get(newResource.device).resources.add(newResource);

                }


            }

            // return all retrieved devices
            return new ArrayList<LSLCDevice>(devices.values());







>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
<<<<<<< HEAD
        }
        ArrayList<LSLCDevice> ret = new ArrayList<LSLCDevice>();

        return ret;
=======
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // return empty list
        return new ArrayList<LSLCDevice>();
>>>>>>> 2e284a4ed2fe4d1acd38c469162ed3534557f0d1
    }
}
