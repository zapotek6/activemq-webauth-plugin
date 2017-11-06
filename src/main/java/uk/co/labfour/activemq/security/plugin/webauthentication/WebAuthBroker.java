package uk.co.labfour.activemq.security.plugin.webauthentication;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.security.SecurityAdminMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class WebAuthBroker extends BrokerFilter implements SecurityAdminMBean {

    private final Logger logger;
    public final static String REDIS_KEY = "authentication:activemq:tokens";

    ConfigStore configStore;
    WebAuthClient webAuthClient;

    public WebAuthBroker(Broker next, ConfigStore configStore, WebAuthClient webAuthClient, Logger logger) {
        super(next);
        this.configStore = configStore;
        this.webAuthClient = webAuthClient;
        this.logger = logger;

        prepareRedisConnectionString();
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception {



        //String redisUri = configStore.getVar(PluginVariables.REDIS_CONNECTION_STRING);

        //logger.debug("Establishing Redis connection using uri={} ", redisUri);
        //Jedis jedis = new Jedis(redisUri);

        String username = info.getUserName();
        String password = info.getPassword();

        boolean isAuthenticated = false;

        try {
            isAuthenticated = webAuthClient.authenticate(username, password);
        } catch (Exception e) {
            throw new SecurityException("Error during calling web authentication server");
        }

        /*logger.debug("Querying Redis using [key={}, username={}] ", REDIS_KEY, username);
        String response = jedis.hget(REDIS_KEY, username);
        */

        if(isAuthenticated) {
            logger.debug("Found username [{}]. Allowing connection", username);
            super.addConnection(context, info);
        } else {
            throw new SecurityException("Invalid credentials");
        }
    }

    @Override
    public void addSession(ConnectionContext context, SessionInfo info) throws Exception {
        super.addSession(context, info);
    }


    private void prepareRedisConnectionString() {
        configStore.setVar(PluginVariables.REDIS_CONNECTION_STRING, "redis://"+ configStore.getVar(PluginVariables.REDIS_HOST) + ":" + configStore.getVar(PluginVariables.REDIS_PORT) + "/" + configStore.getVar(PluginVariables.REDIS_DB));
    }

    public void addRole(String s) {

    }

    public void removeRole(String s) {

    }

    public void addUserRole(String s, String s1) {

    }

    public void removeUserRole(String s, String s1) {

    }

    public void addTopicRole(String s, String s1, String s2) {

    }

    public void removeTopicRole(String s, String s1, String s2) {

    }

    public void addQueueRole(String s, String s1, String s2) {

    }

    public void removeQueueRole(String s, String s1, String s2) {

    }
}
