package uk.co.labfour.activemq.security.plugin.webauthentication;

import org.slf4j.Logger;

import java.util.HashMap;

public class ConfigStore {
    HashMap<String, String> vars = new HashMap<String, String>();

    private final Logger logger;

    public ConfigStore(Logger logger) {
        this.logger = logger;
    }

    private String getEnvVariable(String varname, String fallbackValue) {
        String env = System.getenv(varname);

        if (null == env) {
            env = fallbackValue;
            logger.warn("Env " + varname + " is not set. Using fallback value " + fallbackValue);
        } else {
            logger.info("Env " + varname + ": " + env);
        }

        return env;
    }

    public void addVar(String varName, String fallbackValue) {
        String varValue = getEnvVariable(varName, fallbackValue);
        vars.put(varName, varValue);
    }

    public String getVar(String varName) {
        if (vars.containsKey(varName)) {
            return vars.get(varName);
        } else {
            return null;
        }
    }

    public void setVar(String varName, String varValue) {
        if (vars.containsKey(varName)) {
            vars.replace(varName, varValue);
        } else {
            vars.put(varName, varValue);
        }
    }

}
