# ActiveMQ Web Authentication Plugin

## Introduction

This plugin was inspired to the **rabbitmq_auth_backend_http plugin**.

That plugin take the credentials provided by the client and verify the validity against a web application.
This implementation 

```$xslt
https://server:port/path?username=bob&password=mysecret
```

You can configure the scheme, host, port and path with the environment variable **WEBAUTH_SERVER_URL**

example:
```$xslt
WEBAUTH_SERVER_URL="https://server:port/path"
```

## How to install

- compile the project

- put the jar and all its dependencies in the lib folder of the ActiveMQ installation.
  In order to get all the depencencies run the copy-dependencies.sh script from the lib folder

    ```
    activemq-webauth-plugin/lib# copy-dependencies.sh
    ```
- configure the plugin in the activemq.xml. Add the bean element in the plugins section inside the broker section.

    ```$xslt
        <broker> 
            <plugins>
                <bean xmlns="http://www.springframework.org/schema/beans" id="webAuthPlugin" class="uk.co.labfour.activemq.security.plugin.webauthentication.WebAuthPlugin" >
                </bean>
            </plugins>
        </broker>
    
    ```
- setup the WEBAUTH_SERVER_URL environment variable
    ```$xslt
    WEBAUTH_SERVER_URL="https://server:port/path"
    ```


## Using SSL endpoint

When you connect to a server using HTTPS you may face some connection error.

In this case verify that you machine/container has all the necessary CA certificates. Usually located in _/etc/ssl/certs/java/cacerts_

Try to update the ca certs, try to use the following command 


```$xslt
sudo update-ca-certificates -f
```

If that command does not exists try to install the package **ca-certificates-java**.

If even with this actions you still get error try to setup the **CA_CERTS_STORE_PATH** environment variable

```$xslt
CA_CERTS_STORE_PATH=/etc/ssl/certs/java/cacerts
```



[ActiveMQ Interceptors](http://activemq.apache.org/interceptors.html)

<<<<<<< HEAD
[Apache ActiveMQ source examples](http://svn.apache.org/repos/asf/activemq/trunk/activemq-broker/src/main/java/org/apache/activemq/)
=======
[Apache ActiveMQ source examples](http://svn.apache.org/repos/asf/activemq/trunk/activemq-broker/src/main/java/org/apache/activemq/)
>>>>>>> a22952dbc52238e14d27a82b1bac2adc16b78da7
