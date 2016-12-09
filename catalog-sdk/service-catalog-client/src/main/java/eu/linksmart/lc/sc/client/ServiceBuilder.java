package eu.linksmart.lc.sc.client;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import eu.linksmart.lc.sc.types.Endpoint;
import eu.linksmart.lc.sc.types.Protocol;
import eu.linksmart.lc.sc.types.Registration;
import eu.linksmart.lc.sc.types.Representation;
import eu.linksmart.lc.sc.types.TextPlain;

public class ServiceBuilder {
	
	public static Registration createRegistration(String jsonFileName) {
		Reader reader = new InputStreamReader(ServiceBuilder.class.getResourceAsStream(jsonFileName));
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
	 * creates a service registration for given ID & Name with default protocol type REST 
	 */
	public static Registration createRegistration(String serviceName, String url) {
		
		Registration registration = new Registration();
		
		registration.setName(serviceName);
		registration.setDescription("description");
		
		Protocol protocol = new Protocol();
		protocol.setType("REST");
		
		Endpoint endpoint = new Endpoint();
		endpoint.setUrl(url);
		protocol.setEndpoint(endpoint);
		
		List<String> methodsList = new ArrayList<String>();
		methodsList.add("GET"); 
		protocol.setMethods(methodsList);
		
		List<String> contentTypeList = new ArrayList<String>();
		contentTypeList.add("text/plain"); 
		protocol.setContentTypes(contentTypeList);
		
		List<Protocol> protocolList = new ArrayList<Protocol>();
		protocolList.add(protocol);
		registration.setProtocols(protocolList);
		
		Representation representation = new Representation();
		TextPlain repre = new TextPlain();
		repre.setType("string");
		representation.setTextPlain(repre);
		registration.setRepresentation(representation);
		
		return registration;
	}
	
	/*
	 * creates a service registration for given ID & Name with one resource for protocol type MQTT 
	 */
	public static Registration createMqttRegistration(String serviceName, String brokerEndpoint) {
		
		Registration registration = new Registration();
		
		registration.setName(serviceName);
		registration.setDescription("description");
		
		Protocol protocol = new Protocol();
		protocol.setType("MQTT");
		
		Endpoint endpoint = new Endpoint();
		endpoint.setUrl(brokerEndpoint);
		
		protocol.setEndpoint(endpoint);
		
		List<String> methodsList = new ArrayList<String>();
		methodsList.add("PUB"); 
		methodsList.add("SUB");
		
		protocol.setMethods(methodsList);
		
		//
		//does it required?
		// 
		List<String> contentTypeList = new ArrayList<String>();
		contentTypeList.add("text/plain"); 
		protocol.setContentTypes(contentTypeList);
		
		List<Protocol> protocolList = new ArrayList<Protocol>();
		protocolList.add(protocol);
		registration.setProtocols(protocolList);
		
		Representation representation = new Representation();
		TextPlain repre = new TextPlain();
		repre.setType("string");
		representation.setTextPlain(repre);
		registration.setRepresentation(representation);
		
		return registration;
	}
	
}
