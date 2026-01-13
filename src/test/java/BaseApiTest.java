import java.util.*;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.databind.ObjectMapper;

import gorest.api.models.Post;
import gorest.api.models.User;

public class BaseApiTest {

    protected static String convertMapToJson(Map<String, Object> map) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void assertUserEquality(User actualUser, User expectedUser) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualUser.getName(), expectedUser.getName(), "User name is not correct!");
        softAssert.assertEquals(actualUser.getEmail(), expectedUser.getEmail(), "User email is not correct!");
        softAssert.assertEquals(actualUser.getGender(), expectedUser.getGender(), "User gender is not correct!");
        softAssert.assertEquals(actualUser.getStatus(), expectedUser.getStatus(), "User status is not correct!");
        softAssert.assertAll("User details mismatch after creation!");
    }

    void assertPostEquality(Post actual, Post expected) {
        Assert.assertEquals(actual.getUserId(), expected.getUserId(), "User ID doesn't match");
        Assert.assertEquals(actual.getTitle(), expected.getTitle(), "Title doesn't match");
        Assert.assertEquals(actual.getBody(), expected.getBody(), "Body doesn't match");
    }
}
