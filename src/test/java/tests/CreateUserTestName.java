package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.UserCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.UserModelResponse;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


    public class CreateUserTestName {

        private final String BASE_URL = "https://reqres.in/api/users";
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Test
        public void testCreateUserWithoutJob() throws JsonProcessingException {
            UserCredentials user = new UserCredentials("morpheus");

            step("Отправляем POST - запрос без должности");
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

            step("Проверяем, что имя совпадает с теми, что было отпралено");
            assertEquals(user.getName(), userModelResponse.getName(), "Имя пользователя не совпадает");
            assertEquals(user.getJob(), userModelResponse.getJob(), "Профессия пользователя не совпадает");
        }

    }

