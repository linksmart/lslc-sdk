package eu.linksmart.testing;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.UUID;

/**
 * Created by carlos on 22.10.14.
 */
public class testMain {


    public static void main(String[] args) {



        // create root resource document
        JsonObject aResource = new JsonObject();
        // add simple types
        aResource.addProperty("id", "test-dc/DeviceA/"+UUID.randomUUID().toString());
        aResource.addProperty("type","Resource");
        aResource.addProperty("name","Device A");
        aResource.addProperty("device","device-a");


        // create array of protocols
        JsonArray resourceProtocolArray = new JsonArray();
        // create one protocol item
        JsonObject protocol = new JsonObject();
        protocol.addProperty("type","REST");

        JsonObject endpoint = new JsonObject();
        endpoint.addProperty("url","http://device_a");

        protocol.add("endpoint",endpoint);

        JsonArray methods = new JsonArray();
        JsonObject method = new JsonObject();

        JsonPrimitive item = new JsonPrimitive("GET");
        methods.add(item);
        protocol.add("methods",methods);


        JsonArray contentTypes = new JsonArray();
        JsonPrimitive contentType = new JsonPrimitive("text/plain");
        contentTypes.add(contentType);
        protocol.add("contentTypes",contentTypes);

        // add protocol to protocol array
        resourceProtocolArray.add(protocol);
        // add protocol array to root document
        aResource.add("protocols",resourceProtocolArray);

        // create representation document . WTF quite complex type with double layer. SIMPLIFY !
        JsonObject representation = new JsonObject();
        JsonObject textPlain = new JsonObject();
        textPlain.addProperty("type","number");
        representation.add("text/plain",textPlain);
        aResource.add("representation",representation);

        System.out.println(aResource.toString());

        // create root registration document
        JsonObject aRegistration = new JsonObject();
        aRegistration.addProperty("id","test-dc/DeviceA");
        aRegistration.addProperty("type","Device");
        aRegistration.addProperty("name","Device A");
        aRegistration.addProperty("description","Description - Device A");
        aRegistration.addProperty("ttl",60);

        // create array of  resources
        JsonArray resourcesArray = new JsonArray();
        resourcesArray.add(aResource);

        aRegistration.add("resources",resourcesArray);


        System.out.println(aRegistration.toString());


    }

}
