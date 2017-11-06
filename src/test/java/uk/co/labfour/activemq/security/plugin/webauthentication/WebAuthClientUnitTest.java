package uk.co.labfour.activemq.security.plugin.webauthentication;

import com.google.common.annotations.VisibleForTesting;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebAuthClientUnitTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Ignore
    @Test
    public void run() {

        WebAuthClient webAuthClient = new WebAuthClient("https://yourserver/webauth/user", logger);

        try {
            webAuthClient.authenticate("99-YTd1bnUxcTlxZW1hcA==", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
