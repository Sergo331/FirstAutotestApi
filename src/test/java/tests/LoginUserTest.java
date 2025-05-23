package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.LoginRequest;
import models.LoginResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginUserTest {
    private final String BASE_URL = "https://reqres.in/api/login";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest
    @CsvSource({
            "'eve.holt@reqres.in','cityslicka', true",
            "'eve.holt@reqres.in','',false",
            "'peter@klaven','', false"
    })

    public void testLoginUser(String email, String password,boolean isSuccess) throws JsonProcessingException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        step("Отправка POST запроса на авторизацию");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("x-api-key","reqres-free-v1")
                .body(loginRequest)
                .when()
                .post(BASE_URL)
                .then()
                .extract()
                .response();

        if(isSuccess) {
            step("Проверяем успешную авторизацию, статус код 200");
            assertEquals(200, response.getStatusCode(), "Статус код не соответствует 200");

            LoginResponse loginResponse = objectMapper.readValue(response.asString(), LoginResponse.class);

            step("Проверяем, что в ответе присутствует token");
            assertNotNull(loginResponse.getToken(), "Token не должен быть null");
        }

        else {
            step("Проверка неуспешной авторизации, статус код 400");
            assertEquals(400, response.getStatusCode(), "Статус код не соответсвует 400");

            LoginResponse loginResponse = objectMapper.readValue(response.asString(), LoginResponse.class);

            String errorMassage = loginResponse.getError();
            step("Проверяем сообщение об ошибке");
            assertNotNull(errorMassage, "Сообщение об ошибке не должно бытьб null");
            assertEquals("Missing password", errorMassage, "Сообщение об ошибке не совпадает");
        }
    }
}
