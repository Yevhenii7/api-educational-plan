package gorest.api.enums.rest;

public enum PostField implements IField {

    ID("id"),
    USER_ID("=postUserId"),
    TITLE("=postTitle"),
    BODY("=postBody");

    private final String field;

    PostField(String field) {
        this.field = field;
    }

    @Override
    public String getField() {
        return field;
    }
}
