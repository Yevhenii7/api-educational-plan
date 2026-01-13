import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import gorest.api.enums.HttpMethod;
import gorest.api.enums.rest.ApiResponse;
import gorest.api.factory.UserFactory;
import gorest.api.models.Post;
import gorest.api.models.User;
import gorest.api.services.rest.RestApiService;
import gorest.api.utils.JsonUtil;

public class ApiRestTest extends BaseApiTest {

    @Test(description = "Verify user is created")
    public void testCreateUser() {
        RestApiService apiService = new RestApiService();
        User expectedUser = UserFactory.createRandomUser();
        User createdUser = apiService.createUser(expectedUser);
        assertUserEquality(createdUser, expectedUser);
    }

    @Test(description = "Verify user is updated")
    public void testUpdateUser() {
        RestApiService apiService = new RestApiService();
        User user = apiService.createUser();
        user.setName("Updated_" + System.currentTimeMillis());
        ApiResponse<User> response = apiService.updateUser(user);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Status code should be 200");
        Assert.assertEquals(response.getBody().getName(), user.getName(), "User name should be updated");
    }

    @Test(description = "Verify get user")
    public void testGetUser() {
        RestApiService apiService = new RestApiService();
        int userId = apiService.createUser().getId();
        ApiResponse<User> response = apiService.getUser(userId);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Status code is wrong!");
        Assert.assertNotNull(response.getBody(), "User body is null!");
        Assert.assertEquals(response.getBody().getId(), userId, "User ID does not match");
    }

    @Test(description = "Verify user deleted successfully")
    public void testDeleteUser() {
        RestApiService apiService = new RestApiService();
        int userId = apiService.createUser().getId();
        ApiResponse<Void> response = apiService.deleteUser(userId);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NO_CONTENT, "User delete status code is wrong!");
    }

    @Test(description = "Verify post is created")
    public void testCreatePost() {
        RestApiService apiService = new RestApiService();
        User user = apiService.createUser();
        ApiResponse<Post> response = apiService.createPost(user);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED, "Post created status code is not match!");
        Assert.assertNotNull(response.getBody(), "Post body is null!");
        Assert.assertEquals(response.getBody().getUserId(), user.getId(), "Post user ID does not match");
    }

    @Test(description = "Verify post is updated")
    public void testUpdatePost() {
        RestApiService apiService = new RestApiService();
        User user = apiService.createUser();
        ApiResponse<Post> createdPostResponse = apiService.createPost(user);
        int postId = createdPostResponse.getBody().getId();
        Post updatedPost = createdPostResponse.getBody();
        updatedPost.setTitle("Updated title " + System.currentTimeMillis());
        updatedPost.setBody("Updated body " + System.currentTimeMillis());
        String updatedPostJson = JsonUtil.toJson(updatedPost);
        ApiResponse<Post> response = apiService.updatePost(postId, updatedPostJson);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Post updated status code is not match");
        Assert.assertEquals(response.getBody().getTitle(), updatedPost.getTitle(), "Post title was not updated");
        Assert.assertEquals(response.getBody().getBody(), updatedPost.getBody(), "Post body was not updated");
    }
}
