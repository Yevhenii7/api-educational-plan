package gorest.api.builder.graphql;

import java.util.Map;

import org.json.JSONObject;

import gorest.api.enums.graphql.PostPlaceholder;
import gorest.api.enums.graphql.UserPlaceholder;
import gorest.api.models.Post;
import gorest.api.models.User;
import gorest.api.utils.JsonUtil;

public class PayloadGraphQLBuilder {

    private PayloadGraphQLBuilder() {
    }

    public static String buildUserMutation(User user, String templatePath) {
        return applyPlaceholders(Map.of(
          UserPlaceholder.NAME.getPlaceholder(), user.getName(),
          UserPlaceholder.EMAIL.getPlaceholder(), user.getEmail(),
          UserPlaceholder.GENDER.getPlaceholder(), user.getGender(),
          UserPlaceholder.STATUS.getPlaceholder(), user.getStatus()
        ), templatePath);
    }

    public static String buildPostMutation(Post post, String templatePath) {
        return applyPlaceholders(Map.of(
          PostPlaceholder.USER_ID.getPlaceholder(), String.valueOf(post.getUserId()),
          PostPlaceholder.TITLE.getPlaceholder(), post.getTitle(),
          PostPlaceholder.BODY.getPlaceholder(), post.getBody()
        ), templatePath);
    }

    private static String applyPlaceholders(Map<String, String> data, String templatePath) {
        JSONObject jsonPayload = JsonUtil.loadJsonTemplate(templatePath);
        String jsonAsString = jsonPayload.toString();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            jsonAsString = jsonAsString.replace(entry.getKey(), entry.getValue());
        }

        return jsonAsString;
    }
}