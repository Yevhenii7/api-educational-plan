package gorest.api.enums.graphql;

public enum PostPlaceholder implements IPlaceholder {

    ID("=postId"),

    USER_ID("=postUserId"),

    TITLE("=postTitle"),

    BODY("=postBody");

    private final String placeholder;

    PostPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }
}
