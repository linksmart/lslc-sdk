package eu.linksmart.lc.rc.client;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigurationProvider
{

    private static Logger log = Logger.getLogger( ConfigurationProvider.class );

    private static final String BUNDLE_NAME = "/rc-client-config.properties"; //$NON-NLS-1$

    private ConfigurationProvider()
    {
    }

    /**
     * Reads the value for the given key from the component configuration file. If there is a system property
     * with the same key, the system property will override the value from the configuration file.
     * 
     * @param key
     *            key of the configuration property
     * 
     * @return the configured value
     */
    public static String getString( String key )
    {
        try
        {
            Properties bundle = new Properties();
            InputStream in = ConfigurationProvider.class.getResource( BUNDLE_NAME ).openStream();
            bundle.load( in );
            //
            // if there is a system property with the same key, the system property will be used instead of
            // the value from the configuration file.
            //
            String defaultValue = bundle.getProperty( key );
            return System.getProperty( key, defaultValue );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
            // return '!' + key + '!';
        }
    }

    public static boolean getBoolean( String key, boolean defaultValue )
    {
        try
        {
            Properties bundle = new Properties();
            InputStream in = ConfigurationProvider.class.getResource( BUNDLE_NAME ).openStream();
            bundle.load( in );
            return Boolean.parseBoolean( bundle.getProperty( key, Boolean.valueOf( defaultValue ).toString() ) );
        }
        catch ( Exception e )
        {
            log.error( "failed to retrive property !" + key + "!" );
            return defaultValue;
        }
    }
}
