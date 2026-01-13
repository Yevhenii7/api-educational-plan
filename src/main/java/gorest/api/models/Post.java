package gorest.api.models;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class Post {

    @JsonProperty("id")
    private int id;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;
}
