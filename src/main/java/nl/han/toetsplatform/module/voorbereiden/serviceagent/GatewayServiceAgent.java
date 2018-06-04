package nl.han.toetsplatform.module.voorbereiden.serviceagent;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import nl.han.toetsplatform.module.voorbereiden.exceptions.GatewayCommunicationException;

public class GatewayServiceAgent implements IGatewayServiceAgent{
    private String baseUrl;
    private Client client;

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
            throw new GatewayCommunicationException();
        }

        // getting json string and converting to object
        Gson gson = new Gson();
        String json = response.getEntity(String.class);
        return gson.fromJson(json, type);
    }

    public <T> void post(String resourceUrl, T entity) throws GatewayCommunicationException {
        // request to server
        WebResource webResource = client.resource(this.baseUrl).path(resourceUrl);

        // reading response from server
        ClientResponse response = webResource.post(ClientResponse.class, entity);
        if (response.getStatus() != 200) {
            throw new GatewayCommunicationException();
        }
    }
}
