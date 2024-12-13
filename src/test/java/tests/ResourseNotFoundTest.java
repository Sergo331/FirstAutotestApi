package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Проверка одного ресурса")
@Feature("Получает информацию о ресурсе")
public class ResourseNotFoundTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Story("Проверка получения данных несуществующего ресурса")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testResourseNotFound() throws Exception {
        step("Отправка GET - запроса");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/555")
                .then()
                .statusCode(404)
                .extract()
                .response();

        step("Тело ответа пустое");
        String responsebody = response.getBody().asString();
        assertEquals("{}", responsebody, "Тело ответа не является пустым");
    }
}
