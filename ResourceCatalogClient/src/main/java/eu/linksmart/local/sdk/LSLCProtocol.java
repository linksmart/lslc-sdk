package eu.linksmart.local.sdk;

import javax.mail.internet.ContentType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carlos on 24.10.14.
 */
public class LSLCProtocol {

    public String type;
    //public URL endpoint;
    public List<String> methods;
    public List<ContentType> contentTypes;
    public HashMap<String, String> endpoint;

    public LSLCProtocol(){
        methods = new ArrayList<String>();
        contentTypes = new ArrayList<ContentType>();
        endpoint = new HashMap<String,String>();
    }
}
