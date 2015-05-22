package eu.linksmart.lc.rc.client;

import org.junit.Test;

import javax.json.*;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;

public class JsonDocumentTest {
	
	@Test
    public void testJsonDocument() {
    	
    	try {


			URL aURL =this.getClass().getResource("/registration.json");
			System.out.println("aURL :"+aURL.toString());
			File jsonDataFile = null;
			try {
				jsonDataFile = new File(aURL.toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

    		//File jsonDataFile = new File((this.getClass().getResource(("/registration.json")).getFile()));
    		
    		JsonReader reader = Json.createReader(new FileReader(jsonDataFile));
        	//JsonStructure jsonst = reader.read();
        	
        	JsonObject object = reader.readObject();
        	
        	for (String name : object.keySet())
    	            navigateTree(object.get(name), name);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    private void navigateTree(JsonValue tree, String key) {
 	   if (key != null)
 	      System.out.print("Key " + key + ": ");
 	   switch(tree.getValueType()) {
 	      case OBJECT:
 	         System.out.println("OBJECT");
 	         JsonObject object = (JsonObject) tree;
 	         for (String name : object.keySet())
 	            navigateTree(object.get(name), name);
 	         break;
 	      case ARRAY:
 	         System.out.println("ARRAY");
 	         JsonArray array = (JsonArray) tree;
 	         for (JsonValue val : array)
 	            navigateTree(val, null);
 	         break;
 	      case STRING:
 	         JsonString st = (JsonString) tree;
 	         System.out.println("STRING " + st.getString());
 	         break;
 	      case NUMBER:
 	         JsonNumber num = (JsonNumber) tree;
 	         System.out.println("NUMBER " + num.toString());
 	         break;
 	      case TRUE:
 	      case FALSE:
 	      case NULL:
 	         System.out.println(tree.getValueType().toString());
 	         break;
 	   }
    }
}
