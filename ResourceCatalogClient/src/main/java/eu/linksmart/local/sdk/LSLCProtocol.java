package eu.linksmart.local.sdk;

import javax.mail.internet.ContentType;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 24.10.14.
 */
public class LSLCProtocol {

    public String type;
    public URL endpoint;
    public List<String> methods;
    public List<ContentType> contentTypes;

    public LSLCProtocol(){
        methods = new ArrayList<String>();
        contentTypes = new ArrayList<ContentType>();
    }
}
