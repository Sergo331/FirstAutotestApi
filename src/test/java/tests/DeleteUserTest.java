package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteUserTest{

    private final String BASE_URL = "https://reqres.in/api/users/2";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testDeleteUser() throws Exception {
        step("Отправка DELETE - запроса");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URL)
                .then()
                .statusCode(204)
                .extract()
                .response();

        step("Тело ответа пустое");
        String responsebody = response.getBody().asString();
        assertEquals(" ", responsebody, "Тело ответа не является пустым");
    }
}
