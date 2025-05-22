package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.UserCredentials;
import models.UserModelResponse;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateUserTestPatch {

    private final String BASE_URL = "https://reqres.in/api/users";
    private final com.fasterxml.jackson.databind.ObjectMapper ObjectMapper = new ObjectMapper();

    @Test
    public void testUpdateUserPut() throws JsonProcessingException {
        UserCredentials user = new UserCredentials("morpheus");

        step("Отправляем PATCH - запрос");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("x-api-key","reqres-free-v1")
                .body(user)
                .when()
                .patch(BASE_URL + "/2")
                .then()
                .statusCode(200)
                .extract()
                .response();

        step("Десериализация JSON - ответа в объект UserModelResponse");
        UserModelResponse userModelResponse = ObjectMapper.readValue(response.asString(), UserModelResponse.class);

        step("Проверяем, что в ответе присутствуют id и createAt");
        assertNotNull(userModelResponse.getUpdatedAt(), "UpdatedAt не должно быть null");

        step("Проверяем, что имя и работа совпадают с теми, что было отпралены");
        assertEquals(user.getName(), userModelResponse.getName(), "Имя пользователя не совпадает");
        assertEquals(user.getJob(), userModelResponse.getJob(), "Профессия пользователя не совпадает");

    }
}


