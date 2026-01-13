package gorest.api.builder.rest;

import java.util.Map;

import org.json.JSONObject;

import gorest.api.enums.rest.PostField;
import gorest.api.enums.rest.UserField;
import gorest.api.models.Post;
import gorest.api.models.User;
import gorest.api.utils.JsonUtil;

public class PayloadRestBuilder {

    private PayloadRestBuilder() {
    }

    public static String buildUserPayload(User user, String templatePath) {
        return applyPlaceholders(Map.of(
          UserField.NAME.getField(), user.getName(),
          UserField.EMAIL.getField(), user.getEmail(),
          UserField.GENDER.getField(), user.getGender(),
          UserField.STATUS.getField(), user.getStatus()
        ), templatePath);
    }

    public static String buildPostPayload(int userId, Post post, String templatePath) {
        return applyPlaceholders(Map.of(
          PostField.USER_ID.getField(), String.valueOf(userId),
          PostField.TITLE.getField(), post.getTitle(),
          PostField.BODY.getField(), post.getBody()
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