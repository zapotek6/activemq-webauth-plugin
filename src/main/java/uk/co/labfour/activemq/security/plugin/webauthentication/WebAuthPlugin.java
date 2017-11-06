package uk.co.labfour.activemq.security.plugin.webauthentication;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebAuthPlugin implements BrokerPlugin {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    ConfigStore configStore = new ConfigStore(logger);
    WebAuthClient webAuthClient;

    public WebAuthPlugin() {
        //System.err.println("--------------------------------------> Plugin ctor");
    }

    public Broker installPlugin(Broker broker) throws Exception {

        configStore.addVar(PluginVariables.REDIS_HOST, "localhost");
        configStore.addVar(PluginVariables.REDIS_PORT, "6379");
        configStore.addVar(PluginVariables.REDIS_DB, "0");
        configStore.addVar(PluginVariables.REDIS_CONNECTION_STRING, null);
        configStore.addVar(PluginVariables.WEBAUTH_SERVER_URL, "https://yourserver/webauth/user");
        configStore.addVar(PluginVariables.CA_CERTS_STORE_PATH, null);

        System.out.println(PluginVariables.WEBAUTH_SERVER_URL + ": " + configStore.getVar(PluginVariables.WEBAUTH_SERVER_URL));
        System.out.println(PluginVariables.CA_CERTS_STORE_PATH + ": " + configStore.getVar(PluginVariables.CA_CERTS_STORE_PATH));


        if (null != configStore.getVar(PluginVariables.CA_CERTS_STORE_PATH)) {
            System.setProperty("javax.net.ssl.trustStore", configStore.getVar(PluginVariables.CA_CERTS_STORE_PATH));
            System.setProperty("javax.net.ssl.trustAnchors", configStore.getVar(PluginVariables.CA_CERTS_STORE_PATH));
        }


        webAuthClient = new WebAuthClient(configStore.getVar(PluginVariables.WEBAUTH_SERVER_URL), logger);

        return new WebAuthBroker(broker, configStore, webAuthClient, logger);
    }

}
