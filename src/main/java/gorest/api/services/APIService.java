package gorest.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import gorest.api.enums.HttpMethod;
import gorest.api.models.Post;
import gorest.api.models.User;

import static gorest.api.constants.ConfigConstant.API_ACCESS_TOKEN;
import static gorest.api.constants.ConfigConstant.DEFAULT_API_URL;

public class APIService {

    private final String API_URL;
    private final String TOKEN;

    public APIService() {
        this(DEFAULT_API_URL, API_ACCESS_TOKEN);
    }

    public APIService(String apiUrl, String token) {
        this.API_URL = apiUrl;
        this.TOKEN = token;
    }

    public User createUser() {
        HttpRequest request = createRequest("users", getUserBody(), HttpMethod.POST);
        return sendRequest(request, User.class);
    }

    public Post createPost() {
        User user = createUser();
        return createPost(user);
    }

    public Post createPost(User user) {
        HttpRequest request = createRequest("posts", getPostBody(user), HttpMethod.POST);
        return sendRequest(request, Post.class);
    }

    public  <T> T sendRequest(HttpRequest request, Class<T> clazz) {
        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return mapToObject(response.body(), clazz);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpRequest createRequest(String path, String body, HttpMethod method) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + path))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + TOKEN);

        switch (method) {
            case GET:
                return requestBuilder.GET().build();
            case POST:
                return requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body)).build();
            case PUT:
                return requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(body)).build();
            case DELETE:
                return requestBuilder.DELETE().build();
            default:
                throw new IllegalArgumentException("Unsupported method: " + method);
        }
    }

    public String getUserBody() {
        return getJsonString(Map.of(
                "name", "Test User+" + System.currentTimeMillis(),
                "email", "testuser+" + System.currentTimeMillis() + "@gmail.com",
                "gender", "male",
                "status", "active"
        ));
    }

    public String getPostBody() {
        User user = createUser();
        return getPostBody(user);
    }

    public String getPostBody(User user) {
        return getJsonString(Map.of(
                "user", user.getName(),
                "user_id", String.valueOf(user.getId()),
                "title", "Test title+" + System.currentTimeMillis(),
                "body", "Test body+" + System.currentTimeMillis()
        ));
    }

    private String getJsonString(Map<String, String> values) {
        try {
            return new ObjectMapper().writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T mapToObject(String body, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(body, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}