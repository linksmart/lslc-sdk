package eu.linksmart.ls.rc.client;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import eu.linksmart.ls.rc.types.Endpoint;
import eu.linksmart.ls.rc.types.Protocol;
import eu.linksmart.ls.rc.types.Registration;
import eu.linksmart.ls.rc.types.Representation;
import eu.linksmart.ls.rc.types.Resource;
import eu.linksmart.ls.rc.types.TextPlain;

public class DeviceBuilder {
	
	public static Registration createRegistration(String jsonFileName) {
		Reader reader = new InputStreamReader(DeviceBuilder.class.getResourceAsStream(jsonFileName));
		Registration registration = new Gson().fromJson(reader, Registration.class);
		return registration;
	}
	
	public static Registration createRegistration(File jsonFile) {
		Reader reader = null;
		Registration registration = null;
		try {
			reader = new FileReader(jsonFile);
			registration = new Gson().fromJson(reader, Registration.class);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return registration;
	}
	
	public static Registration createRegistration(String deviceID, String deviceName, String resourceID, String resourceName, String URL) {
		
		Registration registration = new Registration();
		registration.setId(deviceID);
		registration.setType("Device");
		registration.setName(deviceName);
		registration.setDescription(deviceID + "description");
		registration.setTtl(30);
		
		Resource resource = new Resource();
		resource.setId(resourceID);
		resource.setType("Resource");
		resource.setName(resourceName);
		
		Protocol protocol = new Protocol();
		protocol.setType("REST");
		
		Endpoint endpoint = new Endpoint();
		endpoint.setURL(URL);
		protocol.setEndpoint(endpoint);
		
		List<String> methodsList = new ArrayList<String>();
		methodsList.add("GET"); 
		protocol.setMethods(methodsList);
		
		List<String> contentTypeList = new ArrayList<String>();
		contentTypeList.add("text/plain"); 
		protocol.setContentTypes(contentTypeList);
		
		List<Protocol> protocolList = new ArrayList<Protocol>();
		protocolList.add(protocol);
		resource.setProtocols(protocolList);
		
		Representation representation = new Representation();
		TextPlain repre = new TextPlain();
		repre.setType("string");
		representation.setTextPlain(repre);
		resource.setRepresentation(representation);
		
		List<Resource> resources = new ArrayList<Resource>();
		resources.add(resource);
		registration.setResources(resources);
		
		return registration;
	}
	
}
