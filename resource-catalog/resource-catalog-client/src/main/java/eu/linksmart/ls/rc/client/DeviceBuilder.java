package eu.linksmart.ls.rc.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import eu.linksmart.ls.rc.types.Endpoint;
import eu.linksmart.ls.rc.types.Protocol;
import eu.linksmart.ls.rc.types.Registration;
import eu.linksmart.ls.rc.types.Representation;
import eu.linksmart.ls.rc.types.Resource;
import eu.linksmart.ls.rc.types.TextPlain;

public class DeviceBuilder {
	
	public static Registration createRegistration(String jsonString) {
		Registration registration = new Gson().fromJson(jsonString, Registration.class);
		return registration;
	}
	
	public static Registration createRegistration(File jsonFile) {
		String jsonString = readFileContents(jsonFile);
		Registration registration = new Gson().fromJson(jsonString, Registration.class);
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
	
	private static String readFileContents(File jsonFile) {
		
		StringBuilder fileContents = null;
		Scanner scanner = null;
	
		try {
			fileContents = new StringBuilder((int)jsonFile.length());
			scanner = new Scanner(jsonFile);
			String lineSeparator = System.getProperty("line.separator");
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	        scanner.close();
	    }
		return fileContents.toString();
	}

}
