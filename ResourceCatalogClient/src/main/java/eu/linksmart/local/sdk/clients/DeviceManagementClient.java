package eu.linksmart.local.sdk.clients;

import com.google.gson.*;
import eu.linksmart.local.sdk.*;

import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.*;

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
    public String add(LSLCDevice aDevice) {

        String registrationID = hostID+"/"+aDevice.name;

        String payload = createRegistrationPayload(aDevice,registrationID);

        // try to register device inside ResourceCatalog
        String registerEndpoint = ResourceCatalogEndpoint+"/";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(registerEndpoint).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
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
            int responseCode = connection.getResponseCode();
            if(responseCode!=201){
                System.out.println("[WARN] registration response code: "+responseCode);
                return null;
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return registrationID;
    }


    @Override
    public boolean delete(String deviceID) {

        String deleteEndpoint = ResourceCatalogEndpoint.toString()+"/"+deviceID;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(deleteEndpoint).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();

            connection.disconnect();
            if(responseCode==200) return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean update(LSLCDevice aDevice, String aDeviceID) {

        String updateEndpoint = ResourceCatalogEndpoint.toString()+"/"+aDeviceID;

        String payload = createRegistrationPayload(aDevice, aDeviceID);


        // try to update device inside ResourceCatalog
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(updateEndpoint).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();

            int responseCode = connection.getResponseCode();
            if(responseCode!=200){
                System.out.println("[WARN] update response code: "+responseCode);
                return false;
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    public LSLCDevice get(String deviceID) {
        String getEndpoint = ResourceCatalogEndpoint.toString()+"/"+deviceID;
        // try to retrieve device from ResourceCatalog
        try {

            String deviceString = getRawData(new URL(getEndpoint));

            JsonParser parser = new JsonParser();
            JsonElement parsedElement = parser.parse(deviceString);

            JsonObject deviceJSON = parsedElement.getAsJsonObject();

            LSLCDevice aDevice = new LSLCDevice();

            // read simple types
            aDevice.name = deviceJSON.get("name").getAsString();
            aDevice.ttl = deviceJSON.get("ttl").getAsInt();
            aDevice.description = deviceJSON.get("description").getAsString();
            aDevice.id = deviceJSON.get("id").getAsString();

            // read resources from array
            JsonArray resourcesJSON = deviceJSON.getAsJsonArray("resources");
            for(int i=0; i < resourcesJSON.size();i++){
                JsonObject resourceJSON = resourcesJSON.get(i).getAsJsonObject();
                LSLCResource aResource = new LSLCResource();
                // read simple types
                aResource.name = resourceJSON.get("name").getAsString();
                aResource.device = resourceJSON.get("device").getAsString();
                // read protocols from array
                JsonArray protocolsJSON = resourceJSON.getAsJsonArray("protocols");
                for(int j=0; j < protocolsJSON.size(); j++){
                    JsonObject protocolJSON = protocolsJSON.get(j).getAsJsonObject();
                    LSLCProtocol aProtocol = new LSLCProtocol();
                    // read simple types
                    JsonObject endpointJSON = protocolJSON.get("endpoint").getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> alienEndpoints = endpointJSON.entrySet();
                    for(Map.Entry<String, JsonElement> entry : alienEndpoints) {
                        aProtocol.endpoint.put(entry.getKey().toString(),entry.getValue().getAsString());
                    }
                    aProtocol.type = protocolJSON.get("type").getAsString();
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
                    aResource.protocols.add(aProtocol);


                }
                // read representation of resource

                // representation retrieval
                LSLCRepresentation newRepresentation = new LSLCRepresentation();
                JsonObject representationJSON = resourceJSON.get("representation").getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> setOfContentTypes = representationJSON.entrySet();
                // iterate over possible content types
                for(Map.Entry<String, JsonElement> entry : setOfContentTypes) {
                    String key = entry.getKey();
                    JsonElement value = entry.getValue();

                    newRepresentation.contentType = new ContentType(key);
                    newRepresentation.type = value.getAsJsonObject().get("type").getAsString();
                }
                // add resource representation to resource instance
                aResource.representation = newRepresentation;


                aDevice.resources.add(aResource);
            }

            return aDevice;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LSLCDevice> getAllDevices() {
        String getAllDevicesEndpoint = ResourceCatalogEndpoint.toString();
        try {

            // parsing section starts
            String deviceString = getRawData(new URL(getAllDevicesEndpoint));

            JsonParser parser = new JsonParser();
            JsonElement parsedElement = parser.parse(deviceString);

            JsonObject devicesJSON = parsedElement.getAsJsonObject().getAsJsonObject("devices");

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
                // remove empty id bullshit, like rc "device"
                if(!deviceJSON.get("id").toString().equalsIgnoreCase("")){
                    LSLCDevice device = new LSLCDevice();
                    // add simple types
                    device.id = deviceJSON.get("id").getAsString();
                    // TODO workaround for RC bug. cutting of the /rc/
                    device.id = applyIDFix(device.id);
                    device.name = deviceJSON.get("name").getAsString();
                    device.ttl = deviceJSON.get("ttl").getAsInt();
                    device.description = deviceJSON.get("description").getAsString();
                    devices.put(device.id,device);
                }

            }

            // resource array parsing and copying
            JsonArray resourcesJSON = parsedElement.getAsJsonObject().getAsJsonArray("resources");
            // iterate over all resources to find matching device
            for(int i=0; i < resourcesJSON.size(); i++){
                JsonObject resourceJSON = resourcesJSON.get(i).getAsJsonObject();
                // workaround for RC bug
                String resourceID = resourceJSON.get("id").getAsString();
                if(resourceID.equalsIgnoreCase("/rc/")){
                    System.out.println("[WARN] : fake resource from RC found. ignoring.");
                }else{
                    // resource found, creating representation class
                    LSLCResource newResource = new LSLCResource();
                    // copy simple types
                    newResource.id = resourceID;

                    newResource.device = resourceJSON.get("device").getAsString();
                    newResource.device = this.applyIDFix(newResource.device);
                    newResource.name = resourceJSON.get("name").getAsString();

                    // representation retrieval
                    LSLCRepresentation newRepresentation = new LSLCRepresentation();
                    JsonObject representationJSON = resourceJSON.get("representation").getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> setOfContentTypes = representationJSON.entrySet();
                    // iterate over possible content types
                    for(Map.Entry<String, JsonElement> entry : setOfContentTypes) {
                        String key = entry.getKey();
                        JsonElement value = entry.getValue();

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
                        JsonObject endpointJSON = protocolJSON.get("endpoint").getAsJsonObject();

                        for(Map.Entry<String, JsonElement> entry : endpointJSON.entrySet()){
                            newProtocol.endpoint.put(entry.getKey().toString(), entry.getValue().getAsString());
                        }

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
                    devices.get(newResource.device).resources.add(newResource);

                }


            }

            // return all retrieved devices
            ArrayList<LSLCDevice> ret = new ArrayList<LSLCDevice>(devices.values());
            return ret;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // return empty list
        return new ArrayList<LSLCDevice>();
    }
    public String createRegistrationPayload(LSLCDevice aDevice, String aDeviceID){

        int totalResources = aDevice.resources.size();

        // create root registration document
        JsonObject aRegistration = new JsonObject();
        aRegistration.addProperty("id",aDeviceID);
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
                // add fancy super generic "URIs" called brokers for alien hardware from hell
                for(Map.Entry<String, String> entry : aDevice.resources.get(i).protocols.get(j).endpoint.entrySet()){
                    endpoint.addProperty(entry.getKey(), entry.getValue());
                }

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
        return aRegistration.toString();


    }
    public String getRawData(URL restResource) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) restResource.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuffer jsonString = new StringBuffer();
        while ((line = br.readLine()) != null) {
            jsonString.append(line);
        }
        br.close();
        connection.disconnect();

        return jsonString.toString();

    }
    // TODO workaround for RC bug. cutting of the /rc/
    private String applyIDFix(String buggyID){
        System.out.println("[WARN] fixing buggy ID : "+buggyID);
        return buggyID.substring(4,buggyID.length());
    }
}
