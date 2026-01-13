package gorest.api.services.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gorest.api.builder.rest.PayloadRestBuilder;
import gorest.api.constants.RestConstant;
import gorest.api.enums.HttpMethod;
import gorest.api.enums.rest.ApiResponse;
import gorest.api.factory.PostFactory;
import gorest.api.factory.UserFactory;
import gorest.api.models.Post;
import gorest.api.models.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.Map;

import static gorest.api.constants.ConfigConstant.API_ACCESS_TOKEN;
import static gorest.api.constants.ConfigConstant.DEFAULT_API_URL;
import static gorest.api.factory.PostFactory.createPostJson;

public class RestApiService {

    private final String apiUrl;
    private final String token;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RestApiService() {
        this(DEFAULT_API_URL, API_ACCESS_TOKEN);
    }

    public RestApiService(String apiUrl, String token) {
        this.apiUrl = apiUrl;
        this.token = token;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public User createUser() {
        User user = UserFactory.createRandomUser();
        String body = PayloadRestBuilder.buildUserPayload(user, RestConstant.CREATE_USER_TEMPLATE);
        HttpRequest request = buildRequest("users", body, HttpMethod.POST);
        ApiResponse<User> response = sendRequestWithResponse(request, User.class);
        return response.getBody();
    }

    public User createUser(User user) {
        String body = PayloadRestBuilder.buildUserPayload(user, RestConstant.CREATE_USER_TEMPLATE);
        HttpRequest request = buildRequest("users", body, HttpMethod.POST);
        ApiResponse<User> response = sendRequestWithResponse(request, User.class);
        return response.getBody();
    }

    public ApiResponse<User> updateUser(User user) {
        String body = PayloadRestBuilder.buildUserPayload(user, RestConstant.CREATE_USER_TEMPLATE);
        HttpRequest request = buildRequest("users/" + user.getId(), body, HttpMethod.PUT);
        return sendRequestWithResponse(request, User.class);
    }

    public ApiResponse<Post> createPost(User user) {
        Post post = PostFactory.createRandomPost(user);
        String body = PayloadRestBuilder.buildPostPayload(user.getId(), post, RestConstant.CREATE_POST_TEMPLATE);
        HttpRequest request = buildRequest("posts", body, HttpMethod.POST);
        return sendRequestWithResponse(request, Post.class);
    }

    public ApiResponse<User> getUser(int userId) {
        HttpRequest request = buildRequest("users/" + userId, "", HttpMethod.GET);
        return sendRequestWithResponse(request, User.class);
    }

    public ApiResponse<Void> deleteUser(int userId) {
        HttpRequest request = buildRequest("users/" + userId, "", HttpMethod.DELETE);
        return sendRequestWithResponse(request, Void.class);
    }

    public ApiResponse<Post> updatePost(int postId, String postJson) {
        HttpRequest request = buildRequest("posts/" + postId, postJson, HttpMethod.PUT);
        return sendRequestWithResponse(request, Post.class);
    }

    public HttpRequest buildRequest(String path, String body, HttpMethod method) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
          .uri(URI.create(apiUrl + path))
          .header("Authorization", "Bearer " + token)
          .header("Content-Type", "application/json");

        return switch (method) {
            case GET -> builder.GET().build();
            case POST -> builder.POST(HttpRequest.BodyPublishers.ofString(body)).build();
            case PUT -> builder.PUT(HttpRequest.BodyPublishers.ofString(body)).build();
            case DELETE -> builder.DELETE().build();
        };
    }

    public <T> ApiResponse<T> sendRequestWithResponse(HttpRequest request, Class<T> clazz) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            T body = null;
            if (!clazz.equals(Void.class)) {
                body = objectMapper.readValue(response.body(), clazz);
            }
            return new ApiResponse<>(response.statusCode(), body);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to send request or parse response", e);
        }
    }
}
