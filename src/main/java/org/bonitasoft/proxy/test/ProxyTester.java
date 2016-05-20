package org.bonitasoft.proxy.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;


public class ProxyTester {

    private static final String URL = "postUrl";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String PROXY_PROTOCOL = null;
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final String PROXY_HOST = "proxyHost";
    private static final String PROXY_PORT = "proxyPort";
    private static final String PROXY_USER = "proxyUser";
    private static final String PROXY_PASSWORD = "proxyPassword";

    public void start(String propertiesPath) throws IOException {
        final Properties configuration = loadConfiguration(propertiesPath);

        sendPost(configuration);
    }

    protected Properties loadConfiguration(final String propertiesPath) throws IOException {
        final Properties conf = new Properties();
        final File file = new File(propertiesPath);
        if (!file.exists()) {
            throw new FileNotFoundException(propertiesPath);
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            conf.load(fis);
        }
        return conf;
    }

    protected void applyConfiguration(Properties configuration) throws IOException {
        final String protocol = System.getProperty(PROXY_PROTOCOL);

        String key = protocol.toLowerCase() + "." + PROXY_HOST;
        String value = System.getProperty(PROXY_HOST);
        System.setProperty(key, System.getProperty(PROXY_HOST));
        System.out.println(String.format("Setting environment variable: %s = %s", key, value));

        key = protocol.toLowerCase() + "." + PROXY_PORT;
        value = System.getProperty(PROXY_PORT);
        System.setProperty(key, value);
        System.out.println(String.format("Setting environment variable: %s = %s", key, value));

        if ("true".equals(System.getProperty("isProxyWithAuthent"))) {
            key = protocol.toLowerCase() + "." + PROXY_USER;
            value = System.getProperty(PROXY_USER);
            System.setProperty(protocol.toLowerCase() + ".proxyUser", System.getProperty(PROXY_USER));
            System.out.println(String.format("Setting environment variable: %s = %s", key, value));

            key = protocol.toLowerCase() + "." + PROXY_PASSWORD;
            value = System.getProperty(PROXY_PASSWORD);
            System.setProperty(protocol.toLowerCase() + ".proxyPassword", System.getProperty(PROXY_PASSWORD));
            System.out.println(String.format("Setting environment variable: %s = %s", key, value));
        }
    }

    // HTTP POST request
    private void sendPost(Properties configuration) throws IOException {
        final String url = configuration.getProperty(URL);
        final URL obj = new URL(url);
        final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        final String payload = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        final DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(payload);
        wr.flush();
        wr.close();

        final int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post payload : " + payload);
        System.out.println("Response Code : " + responseCode);

        final BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        final StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

}
