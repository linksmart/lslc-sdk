package eu.linksmart.lc.rc.client;

import org.junit.Test;

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

		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
}
