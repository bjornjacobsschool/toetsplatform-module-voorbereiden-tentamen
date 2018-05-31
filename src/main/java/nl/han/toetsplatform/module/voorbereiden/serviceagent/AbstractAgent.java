package nl.han.toetsplatform.module.voorbereiden.serviceagent;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public abstract class AbstractAgent {
    private String baseUrl;
    private Client client;

    public AbstractAgent(String targetServiceUrl) {
        this.client = Client.create( new DefaultClientConfig());
        this.baseUrl = targetServiceUrl;
    }

    protected String get(String resourceUrl) throws Exception {
            WebResource webResource = client.resource(this.baseUrl).path(resourceUrl);

            ClientResponse response = webResource.get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            return response.getEntity(String.class);
    }
//
//    protected Response post(String resourceUrl, Entity<?> entity) {
//        Invocation.Builder request = buildRequest(resourceUrl);
//
//        return request.post(entity);
//    }
}
