//import org.testng.annotations.Test;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.*;
//
//public class GoRestApiTests {
//
//    private final String BASE_URL = "https://gorest.co.in/public-api";
//
//    private final String TOKEN = "7614ee19b13d9a72373f807db0d691a910708f31398c0ccb9ea7989cd544b2bd";
//
//
//    int id;
//
//
//    @Test(priority = 1)
//    public void testGetUser() {
//        given()
//                .header("Authorization", "Bearer " + TOKEN)
//                .when()
//                .get(BASE_URL + "/users/6639067")
//                .then()
//                .statusCode(200)
//                .body("data.id", equalTo(6639067));
//    }
//
//    @Test(priority = 2)
//    public void testCreateUser() {
//        String requestBody = "{ \"name\": \"Kolchybaafddsgfffffff Yevh\", \"email\": \"kolchybqqqwgfffffff.yevh@ex.com\", \"gender\": \"male\", \"status\": \"active\" }";
//
//        id = given()
//                .header("Authorization", "Bearer " + TOKEN)
//                .header("Content-Type", "application/json")
//                .body(requestBody)
//                .when()
//                .post(BASE_URL + "/users")
//                .jsonPath().getInt("data.id");
//
//    }
//
//    @Test(priority = 3, dependsOnMethods = {"testCreateUser"})
//    public void updateUser() {
//        String requestBody = "{ \"name\": \"Kolchybeeeeeeeeeeeee Yevheniy\", \"email\": \"kolchfffeeeeeeeee.yevh@ex.com\", \"gender\": \"male\", \"status\": \"active\" }";
//
//        given()
//                .header("Authorization", "Bearer " + TOKEN)
//                .header("Content-Type", "application/json")
//                .body(requestBody)
//                .when()
//                .put(BASE_URL + "/users/" + id)
//                .then()
//                .statusCode(200)
//                .log().all();
//    }
//
//    @Test(priority = 4, dependsOnMethods = {"testCreateUser", "updateUser"})
//    public void deleteUser() {
//        given()
//                .when()
//                .delete(BASE_URL + "/users/" + id)
//                .then()
//                .statusCode(200)
//                .log().all();
//    }
//
//    @Test(priority = 5)
//    public void testListUser() {
//        given()
//                .header("Authorization", "Bearer " + TOKEN)
//                .when()
//                .get(BASE_URL + "/users")
//                .then()
//                .statusCode(200)
//                .body("code", equalTo(200))
//                .body("data", not(empty()));
//    }
//
//    @Test(priority = 6)
//    public void testInvalidUserCreation() {
//        String requestBody = "{ \"invalid_key\": \"invalid_value\" }";
//        given()
//                .header("Authorization", "Bearer " + TOKEN)
//                .header("Content-Type", "application/json")
//                .body(requestBody)
//                .when()
//                .post(BASE_URL + "/users")
//                .then()
//                .statusCode(200)
//                .body("code", equalTo(422));
//    }
//
//
//    @Test(priority = 7)
//    public void testCreateUserPosts() {
//        String requestBody = "{ \"user_id\": \"6639542\", \"title\": \"Java\", \"body\": \"This is java article.\" }";
//        given()
//                .header("Authorization", "Bearer " + TOKEN)
//                .header("Content-Type", "application/json")
//                .body(requestBody)
//                .when()
//                .post(BASE_URL + "/users/6639542/posts")
//                .then()
//                .statusCode(200)
//                .body("data.title", containsString("Java"));
//
//
//    }
//
//    @Test(priority = 8)
//    public void testCreatePostComments() {
//        String commentJson = "{\"id\":81652,\"post_id\":103080,\"name\":\"Yevhen Kolch\",\"email\":\"kolch_777@gmail.com\",\"body\":\"Have a nice day. Non functional testing.\"}";
//        given()
//                .header("Authorization", "Bearer " + TOKEN)
//                .header("Content-Type", "application/json")
//                .body(commentJson)
//                .post(BASE_URL + "/posts/103080/comments")
//                .then()
//                .statusCode(200)
//                .body("data.name", containsString("Yevhen Kolch"));
//
//    }
//
//    @Test(priority = 9)
//    public void testGetComments() {
//        given()
//                .header("Authorization", "Bearer " + TOKEN)
//                .header("Content-Type", "application/json")
//                .when()
//                .get(BASE_URL + "/posts/103080/comments")
//                .then()
//                .statusCode(200)
//                .body("code", not(empty()));
//    }
//
//    @Test(priority = 10)
//    public void testGetPosts() {
//        given()
//                .header("Authorization", "Bearer " + TOKEN)
//                .header("Content-Type", "application/json")
//                .when()
//                .get(BASE_URL + "/users/6639542/posts")
//                .then()
//                .statusCode(200)
//                .body("data[0].title", containsString("Java"));
//    }
//}
