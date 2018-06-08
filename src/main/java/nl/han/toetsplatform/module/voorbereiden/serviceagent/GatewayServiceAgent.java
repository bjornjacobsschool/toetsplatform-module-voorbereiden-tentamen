package nl.han.toetsplatform.module.voorbereiden.serviceagent;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import nl.han.toetsplatform.module.voorbereiden.applicationlayer.TentamenKlaarzetten;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GatewayServiceAgent implements IGatewayServiceAgent{
    private String baseUrl;
    private Client client;

    private final static Logger LOGGER = Logger.getLogger(TentamenKlaarzetten.class.getName());


    public GatewayServiceAgent() {
        this.client = Client.create( new DefaultClientConfig());
        this.baseUrl = "http://94.124.143.127";
    }

    public <T> T get(String resourceUrl, Class<T> type) throws GatewayCommunicationException {
        // request to server
        WebResource webResource = client.resource(this.baseUrl).path(resourceUrl);

        // reading response from server
        ClientResponse response = webResource.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            LOGGER.log(Level.INFO, "Error connecting to gateway GET: " + response.getStatusInfo());
            throw new GatewayCommunicationException();
        }

        return response.getEntity(type);
    }

    public <T> void post(String resourceUrl, T entity) throws GatewayCommunicationException {
        // request to server
        WebResource webResource = client.resource(this.baseUrl).path(resourceUrl);

        // reading response from server
        ClientResponse response = webResource.post(ClientResponse.class, entity);
        if (response.getStatus() != 200) {
            LOGGER.log(Level.INFO, "Error connecting to gateway POST: " + response.getStatusInfo());
            throw new GatewayCommunicationException();
        }
    }
}
