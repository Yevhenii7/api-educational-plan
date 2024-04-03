//
//import org.testng.annotations.Test;
//
//import io.restassured.response.Response;
//
//import static io.restassured.RestAssured.given;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertTrue;
//
//public class GraphQLTest {
//    private String BASE_URL = "https://gorest.co.in/public/v2/graphql";
//    private final String ACCESS_TOKEN = "7614ee19b13d9a72373f807db0d691a910708f31398c0ccb9ea7989cd544b2bd";
//
//    @Test(priority = 1)
//    public void testQueryUsers() {
//        String graphqlQuery = "{\n" +
//                "  \"query\": \"query User { users(first: 3) { totalCount }}\"\n" +
//                "}";
//
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + ACCESS_TOKEN)
//                .body(graphqlQuery)
//                .when()
//                .post(BASE_URL);
//        assertEquals(response.getStatusCode(), 200, "Unexpected status code");
//        assertTrue(response.getBody().asString().contains("users"), "Response doesn't contain 'users' field");
//    }
//
//    @Test(priority = 2)
//    public void testMutationCreateUser() {
//        String graphqlQuery = "{\n" +
//                "  \"query\": \"mutation CreateUser { createUser( input: { name: \\\"Yevheniiiiiii\\\" email: \\\"kolchibaaaaaaa777@gmail.com\\\" gender: \\\"Male\\\" status: \\\"Active\\\" } ) { user.json { id name gender email status } }}\"\n" +
//                "}";
//
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + ACCESS_TOKEN)
//                .body(graphqlQuery)
//                .when()
//                .post(BASE_URL);
//        assertEquals(response.getStatusCode(), 200, "Unexpected status code");
//        assertEquals(response.jsonPath().getString("data.createUser.user.json.name"), "Yevheniiiiiii", "Incorrect user.json name");
//    }
//
//
//    @Test(priority = 3)
//    public void testMutationUpdateUser() {
//        String updateMutation = "{\n" +
//                "  \"query\": \"mutation UpdateUser { updateUser( input: { id: 6665155 name: \\\"Kol\\\" email: \\\"koka-kola@gmail.com\\\" gender: \\\"male\\\" status: \\\"active\\\" clientMutationId: \\\"7777777\\\" } ) { clientMutationId }}\"\n" +
//                "}";
//
//        Response updateResponse = given()
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + ACCESS_TOKEN)
//                .body(updateMutation)
//                .when()
//                .post(BASE_URL);
//        assertEquals(updateResponse.getStatusCode(), 200, "Unexpected status code");
//        assertEquals(updateResponse.jsonPath().getString("data.updateUser.clientMutationId"), "7777777", "Incorrect user.json name");
//    }
//
//    @Test(priority = 4)
//    public void testMutationDeleteUser() {
//        String deleteMutation = "{\n" +
//                "  \"query\": \"mutation DeleteUser { deleteUser(input: { id: 6664595, clientMutationId: \\\"11111\\\" }) { clientMutationId }}\"\n" +
//                "}";
//
//        Response deleteResponse = given()
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + ACCESS_TOKEN)
//                .body(deleteMutation)
//                .when()
//                .post(BASE_URL);
//        System.out.println(deleteResponse.getBody().asString());
//        assertEquals(deleteResponse.getStatusCode(), 200, "Unexpected status code");
//        assertEquals(deleteResponse.jsonPath().getString("data.deleteUser.clientMutationId"), "11111", "Incorrect user.json name");
//    }
//
//    @Test(priority = 5)
//    public void testQueryPosts() {
//        String graphqlQuery = "{\n" +
//                "  \"query\": \"query Posts { posts { totalCount }}\"\n" +
//                "}";
//
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + ACCESS_TOKEN)
//                .body(graphqlQuery)
//                .when()
//                .post(BASE_URL);
//        assertEquals(response.getStatusCode(), 200, "Unexpected status code");
//        assertEquals(response.jsonPath().getString("data.posts.totalCount"), "2606", "Incorrect user.json name");
//    }
//}
//
