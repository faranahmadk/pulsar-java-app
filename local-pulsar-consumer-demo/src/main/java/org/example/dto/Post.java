package org.example.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Only for Unit Test
 *
 * @param userId
 * @param id
 * @param title
 * @param body
 */

@JsonIncludeProperties({"user_id", "id", "title", "body"})
@JsonPropertyOrder({"title", "body", "id", "user_id"})
public record Post(@JsonAlias({"u_id", "userId"})
                   @JsonProperty("user_id") int userId,
                   int id,
                   String title,
                   String body) {
}