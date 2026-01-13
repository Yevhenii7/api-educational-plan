package gorest.api.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {
    }

    public static String toJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize to JSON", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to map JSON to object", e);
        }
    }

    public static JSONObject loadJsonTemplate(String path) {
        try {
            String content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
            return new JSONObject(content);
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Failed to load or parse JSON template from path: " + path, e);
        }
    }
}
