package uk.co.labfour.activemq.security.plugin.webauthentication;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.slf4j.Logger;

import java.io.IOException;

public class WebAuthClient {
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private static final String ALLOW_RESPONSE = "allow";

    private final Logger logger;
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    String baseUrl;

    public WebAuthClient(String baseUrl, Logger logger) {
        this.baseUrl = baseUrl;
        this.logger = logger;
    }

    public boolean authenticate(String username, String password) throws Exception {
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {

                    public void initialize(HttpRequest request) {

                    }
                });
        GenericUrl url = null;

        url = new GenericUrl(baseUrl);
        url.set(USERNAME_FIELD, username);
        url.set(PASSWORD_FIELD, password);

        HttpRequest request = null;
        try {
            request = requestFactory.buildGetRequest(url);
            return parseResponse(request.execute());
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    private static boolean parseResponse(HttpResponse response) throws IOException {

        if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
            if (ALLOW_RESPONSE.equals(response.parseAsString())) {
                return true;
            }
        }

        return false;
    }
}
