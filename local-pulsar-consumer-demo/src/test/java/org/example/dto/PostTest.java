package org.example.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {
    private ObjectMapper mapper;
    private JSONObject jsonObject;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper().enable(SerializationFeature.WRAP_ROOT_VALUE);
        jsonObject = new JSONObject();
    }

    @Test
    void json_to_pojo() throws JsonProcessingException {
        // Post(int userId, int id, String title, String body)
        jsonObject.put("u_id", 1);
        jsonObject.put("id", 101);
        jsonObject.put("title", "Demo title");
        jsonObject.put("body", "This is demo body");
        jsonObject.put("unknown", "NA");

        Post post = mapper.readValue(jsonObject.toString(), Post.class);
        System.out.println(jsonObject.toString());
        System.out.println(post);
    }

    @Test
    void pojo_to_json() throws JsonProcessingException {
        Post post = new Post(1, 101, "Demo title", "This is demo body");

        String json = mapper.writeValueAsString(post);

        System.out.println(json);
    }

    @DisplayName("Coverts plain JSON into nested POJO")
    @Test
    void plain_json_to_map() throws JsonProcessingException {
        // User(String name, Map<String, String> properties)
        jsonObject.put("name", "faran");
        jsonObject.put("age", 23);
        jsonObject.put("height", 6.8);

        User user = mapper.readValue(jsonObject.toString(), User.class);

        System.out.println(jsonObject.toString());
        System.out.println(user);
    }

    @DisplayName("Flattens the nested POJO into plain JSON")
    @Test
    void map_to_plain_json() throws JsonProcessingException {
        User user = new User("Faran");
        user.setProperty("key1", "value1");
        user.setProperty("key2", "value2");

        String json = mapper.writeValueAsString(user);

        System.out.println(user);
        System.out.println(json);
    }

    @Disabled
    @DisplayName("Add a root property when converting POJO to JSON")
    @Test
    void test() throws JsonProcessingException {
    }

    @DisplayName("Traverse a specific property in JSON")
    @Test
    void test2() throws JsonProcessingException {
        String json = "{\"User\":{\"name\":\"Faran\",\"properties\":{\"key1\":\"value1\",\"key2\":\"value2\"}}}";

        JsonNode jsonNode = mapper.readTree(json);

        String key2 = jsonNode.path("User").path("properties").path("key2").asText();

        System.out.println(key2);
    }
}
