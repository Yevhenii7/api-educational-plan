package gorest.api.enums.graphql;

public enum UserPlaceholder implements IPlaceholder {

    ID("=userId"),

    NAME("=userName"),

    EMAIL("=userEmail"),

    GENDER("=userGender"),

    STATUS("=userStatus");

    private final String placeholder;

    UserPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }
}
