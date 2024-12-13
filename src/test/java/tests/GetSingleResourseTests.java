package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.SingleResourseResponse;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;

@Epic("Проверка одиночного ресурса")
@Feature("Получает информацию о ресурсе")
public class GetSingleResourseTests{
    private final String BASE_URL = "https://reqres.in/api/unknown/2";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Story("Получениф информации о конкретном ресурсе")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testGetSingleResourse() throws Exception {
        step("Отправка GET - запроса");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        step("Десериализация JSON - ответа в объект SingleResourseResponse");
        SingleResourseResponse singleResourseResponse = objectMapper.readValue(response.asString(), SingleResourseResponse.class);
    }
}
