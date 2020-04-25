package com.juno.gateway.config;

import org.jasig.cas.client.ssl.HttpURLConnectionFactory;
import org.jasig.cas.client.ssl.HttpsURLConnectionFactory;
import org.jasig.cas.client.util.IOUtils;
import org.jasig.cas.client.util.SamlUtils;
import org.jasig.cas.client.validation.Saml11TicketValidator;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

/**
 * Created by YukiAkiyama on 2020/4/25.
 */
public class CasClientUrlConnection {

    private static final String SAML_REQUEST_TEMPLATE;

    private HttpURLConnectionFactory httpURLConnectionFactory = new HttpsURLConnectionFactory();

    private final Random random;

    public CasClientUrlConnection() {
        try {
            this.random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Cannot find required SHA1PRNG algorithm");
        }
    }

    static {
        try {
            SAML_REQUEST_TEMPLATE = IOUtils.readString(Saml11TicketValidator.class.getResourceAsStream("/META-INF/cas/samlRequestTemplate.xml"));
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load SAML request template from classpath", e);
        }
    }

    public String retrieveResponseFromServer(URL validationUrl, String ticket) {
        String request = String.format(SAML_REQUEST_TEMPLATE,this.generateId(),SamlUtils.formatForUtcTime(new Date()),ticket);
        HttpURLConnection conn = null;
        String result = null;
        try {
            conn = httpURLConnectionFactory.buildHttpURLConnection(validationUrl.openConnection());
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","text/xml");
            conn.setRequestProperty("Content-Length",Integer.toString(request.length()));
            conn.setRequestProperty("SOAPAction","http://www.oasis-open.org/committees/security");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.getOutputStream().write(request.getBytes(IOUtils.UTF8));
            conn.getOutputStream().flush();
            IOUtils.readString(conn.getInputStream(),IOUtils.UTF8);
        } catch (IOException e) {
            throw new RuntimeException("IO error sending HTTP request to /samlValidate", e);
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    private String generateId() {
        byte[] data = new byte[16];
        this.random.nextBytes(data);
        StringBuilder id = new StringBuilder(33);
        id.append('_');
        for (int i = 0; i < data.length; ++i) {
            id.append("0123456789abcdef".charAt((data[i] & 240) >> 4));
            id.append("0123456789abcdef".charAt(data[i] & 15));
        }
        return id.toString();
    }

}
