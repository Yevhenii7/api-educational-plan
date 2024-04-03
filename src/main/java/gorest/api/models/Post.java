package gorest.api.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {

    @JsonProperty("id")
    private int id;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return id == post.id && userId == post.userId && title.equals(post.title) && body.equals(post.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, body);
    }
}
