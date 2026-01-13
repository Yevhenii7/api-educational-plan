package gorest.api.services.graphql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import gorest.api.constants.JsonConstant;
import gorest.api.models.User;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.Map;

import static gorest.api.constants.ConfigConstant.API_ACCESS_TOKEN;

@Slf4j
public class GraphQLService {

    private final String endpoint;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GraphQLService(String endpoint) {
        this.endpoint = endpoint;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public <T> T sendMutation(String mutation, String jsonPathToData, Class<T> clazz) {
        HttpRequest request = buildPostRequest(mutation);
        HttpResponse<String> response = executeQuery(request);
        if (response.statusCode() != 200) {
            throw new RuntimeException("GraphQL request failed");
        }
        return extractFromResponse(response, jsonPathToData, clazz);
    }


    public HttpRequest buildPostRequest(String payloadJson) {
        return HttpRequest.newBuilder()
          .uri(URI.create(endpoint))
          .header("Authorization", "Bearer " + API_ACCESS_TOKEN)
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(payloadJson))
          .build();
    }

    public HttpResponse<String> executeQuery(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("GraphQL query execution failed", e);
        }
    }

    public <T> T extractFromResponse(HttpResponse<String> response, String jsonPath, Class<T> clazz) {
        try {
            String body = response.body();
            log.info("GraphQL response body:\n" + body);
            Object result = JsonPath.read(body, jsonPath);
            return objectMapper.convertValue(result, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse object from GraphQL response", e);
        }
    }
}