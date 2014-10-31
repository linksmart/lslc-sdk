package eu.linksmart.lc.rc.client;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import eu.linksmart.lc.rc.types.Endpoint;
import eu.linksmart.lc.rc.types.Protocol;
import eu.linksmart.lc.rc.types.Registration;
import eu.linksmart.lc.rc.types.Representation;
import eu.linksmart.lc.rc.types.Resource;
import eu.linksmart.lc.rc.types.TextPlain;

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
	
	/*
	 * creates a device registration for given ID & Name with one resource for protocol type REST 
	 */
	public static Registration createRegistration(String deviceConnectorID, String deviceName, String resourceName, String URL) {
		
		Registration registration = new Registration();
		
		String deviceID = deviceConnectorID + "/" + deviceName;
		
		registration.setId(deviceID);
		registration.setType("Device");
		registration.setName(deviceName);
		registration.setDescription(deviceID + "description");
		registration.setTtl(30);
		
		String resourceID = deviceID + "/" + resourceName;
		
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
