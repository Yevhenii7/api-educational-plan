package gorest.api.enums.rest;

public enum UserField implements IField {
    NAME("=userName"),
    EMAIL("=userEmail"),
    GENDER("=userGender"),
    STATUS("=userStatus");

    private final String field;

    UserField(String field) {
        this.field = field;
    }

    @Override
    public String getField() {
        return field;
    }
}