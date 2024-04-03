import java.io.*;
import java.net.http.*;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import gorest.api.enums.HttpMethod;
import gorest.api.models.User;
import gorest.api.services.APIService;

public class ApiRestTest {

    private final APIService apiService = new APIService();

    @Test(description = "Verify user is created")
    public void testCreateUser() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String userBody = apiService.getUserBody();
        User user = apiService.mapToObject(userBody, User.class);
        HttpRequest request = apiService.createRequest("users", userBody, HttpMethod.POST);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED, "User created status code is wrong!");
        User createdUser = apiService.mapToObject(response.body(), User.class);
        assertUserEquality(createdUser, user, "User details mismatch after creation!");
    }

    @Test(description = "Verify user is updated")
    public void testUpdateUser() throws IOException, InterruptedException {
        int userId = apiService.createUser().getId();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = apiService.createRequest("users/" + userId, apiService.getUserBody(), HttpMethod.PUT);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "User is not updated");
    }

    @Test(description = "Verify get user")
    public void testGetUser() throws IOException, InterruptedException {
        int userId = apiService.createUser().getId();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = apiService.createRequest("users/" + userId, "", HttpMethod.GET);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "status code is wrong!");
    }

    @Test(description = "Verify user deleted successfully")
    public void testDeleteUser() throws IOException, InterruptedException {
        int userId = apiService.createUser().getId();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = apiService.createRequest("users/" + userId, "", HttpMethod.DELETE);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_NO_CONTENT, "User delete status code is wrong!");
    }

    @Test(description = "Verify post is created")
    public void testCreatePost() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = apiService.createRequest("posts",  apiService.getPostBody(), HttpMethod.POST);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED, "Post created status code is wrong!");
    }

    private void assertUserEquality(User actualUser, User expectedUser, String message) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualUser.getName(), expectedUser.getName(), "User name is not correct!");
        softAssert.assertEquals(actualUser.getEmail(), expectedUser.getEmail(), "User email is not correct!");
        softAssert.assertEquals(actualUser.getGender(), expectedUser.getGender(), "User gender is not correct!");
        softAssert.assertEquals(actualUser.getStatus(), expectedUser.getStatus(), "User status is not correct!");
        softAssert.assertAll(message);
    }
}
