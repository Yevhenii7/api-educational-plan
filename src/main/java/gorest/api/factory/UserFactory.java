package gorest.api.factory;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import gorest.api.models.User;

public class UserFactory {

    private static final String[] GENDERS = {"male", "female"};
    private static final String[] STATUSES = {"active", "inactive"};

    public static User createRandomUser() {
        String name = "User_" + UUID.randomUUID().toString().substring(0, 8);
        String email = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        String gender = getRandomElement(GENDERS);
        String status = getRandomElement(STATUSES);

        return new User(name, email, gender, status);
    }

    private static String getRandomElement(String[] array) {
        return array[ThreadLocalRandom.current().nextInt(array.length)];
    }
}

