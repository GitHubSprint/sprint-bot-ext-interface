/*
 * Copyright © 2021 Sprint S.A.
 * Contact: slawomir.kostrzewa@sprint.pl

 */
package pl.sprint.chatbot.ext.lib;

import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Sławomir Kostrzewa
 */
public class Utils {
    
    public static URL createEndpointUrl(String _endpoint, final int timeout) throws MalformedURLException 
    {
        URL endpoint = new URL(null, _endpoint,
                    new URLStreamHandler() { // Anonymous (inline) class
                    @Override
                    protected URLConnection openConnection(URL url) throws IOException 
                    {
                        URL clone_url = new URL(url.toString());
                        HttpURLConnection clone_urlconnection = (HttpURLConnection) clone_url.openConnection();
                        // TimeOut settings
                        clone_urlconnection.setConnectTimeout(timeout);
                        clone_urlconnection.setReadTimeout(timeout);
                        return(clone_urlconnection);
                    }
                });
        
        return endpoint;
        
    }
    
    
    public static void trustAllCertyficates()
    {
        try {
            ////////////////////////////////////////////////////////////////////////////////
            TrustManager[] trustAllCerts = new TrustManager[] 
            {
                    new X509TrustManager() 
                    {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return null;
                            }
                            public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                    return true;
                    }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            /////////////////////////////////////////////////////////////////////////////////
        } catch (Exception ex) {StringWriter sw = new StringWriter();
            ex.printStackTrace();
        }
    }
    
}
