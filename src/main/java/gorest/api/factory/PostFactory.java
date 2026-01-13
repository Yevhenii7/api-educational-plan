package gorest.api.factory;

import java.util.UUID;

import gorest.api.models.Post;
import gorest.api.models.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Post createRandomPost(User user) {
        Post post = new Post();
        post.setUserId(user.getId());
        post.setTitle("Test title " + UUID.randomUUID().toString().substring(0, 8));
        post.setBody("Test body " + UUID.randomUUID().toString().substring(0, 8));
        return post;
    }

    public static String createPostJson(User user) {
        Post post = createRandomPost(user);
        return toJson(post);
    }

    private static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize post", e);
        }
    }
}