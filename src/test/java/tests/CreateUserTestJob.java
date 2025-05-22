package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.UserCredentials;
import models.UserModelResponse;
import org.junit.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CreateUserTestJob {

    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateUserWithoutName() throws JsonProcessingException {
        UserCredentials user = new UserCredentials( " ","leader");

        step("Отправляем POST - запрос без имени");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("x-api-key","reqres-free-v1")
                .body(user)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .extract()
                .response();

        step("Десериализация JSON - ответа в объект UserModelResponse");
        UserModelResponse userModelResponse = objectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем, что в ответе присутствуют id и createAt");
        assertNotNull(userModelResponse.getId(), "Id не должно быть null");
        assertNotNull(userModelResponse.getCreatedAt(), "CreatedAt не должно быть null");

        step("Проверяем, что работа совпадают с теми, что было отпралены");
        assertEquals(user.getJob(), userModelResponse.getJob(), "Профессия пользователя не совпадает");
        assertEquals(user.getName(), userModelResponse.getName(), "Имя пользователя не совпадает");
    }
}