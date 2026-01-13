import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import gorest.api.constants.JsonConstant;
import gorest.api.factory.PostFactory;
import gorest.api.factory.UserFactory;
import gorest.api.models.Post;
import gorest.api.models.User;
import gorest.api.services.graphql.GraphQLService;
import gorest.api.builder.graphql.PayloadGraphQLBuilder;

import static gorest.api.constants.ConfigConstant.GRAPHQL_URL;
import static gorest.api.constants.GraphQLQueryConstant.CREATE_POST_MUTATION_TEMPLATE;
import static gorest.api.constants.GraphQLQueryConstant.CREATE_USER_MUTATION_TEMPLATE;

public class GraphQLTest extends BaseApiTest {

    @Test(description = "Verify that a new user is created using GraphQL mutation")
    public void testCreateUser() {
        GraphQLService graphQLService = new GraphQLService(GRAPHQL_URL);
        User expectedUser = UserFactory.createRandomUser();
        String mutation = PayloadGraphQLBuilder.buildUserMutation(expectedUser, CREATE_USER_MUTATION_TEMPLATE);
        HttpRequest request = graphQLService.buildPostRequest(mutation);
        HttpResponse<String> response = graphQLService.executeQuery(request);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, "User creation failed");
        User actualUser = graphQLService.extractFromResponse(response, JsonConstant.CREATE_USER_RESPONSE, User.class);
        assertUserEquality(actualUser, expectedUser);
    }

    @Test(description = "Verify: create post")
    public void testCreatePost() {
        GraphQLService graphQLService = new GraphQLService(GRAPHQL_URL);
        User user = UserFactory.createRandomUser();
        String userMutation = PayloadGraphQLBuilder.buildUserMutation(user, CREATE_USER_MUTATION_TEMPLATE);
        User createdUser = graphQLService.sendMutation(userMutation, JsonConstant.CREATE_USER_RESPONSE, User.class);
        Post expectedPost = PostFactory.createRandomPost(createdUser);
        String postMutation = PayloadGraphQLBuilder.buildPostMutation(expectedPost, CREATE_POST_MUTATION_TEMPLATE);
        HttpRequest postRequest = graphQLService.buildPostRequest(postMutation);
        HttpResponse<String> postResponse = graphQLService.executeQuery(postRequest);
        Assert.assertEquals(postResponse.statusCode(), HttpStatus.SC_OK, "Post creation failed");
        Post actualPost = graphQLService.extractFromResponse(postResponse, JsonConstant.CREATE_POST_RESPONSE, Post.class);
        assertPostEquality(actualPost, expectedPost);
    }
}