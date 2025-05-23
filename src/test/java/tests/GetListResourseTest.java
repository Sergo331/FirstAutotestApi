package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.ResourseResponse;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Проврка списка ресурсов")
@Feature("Получает информацию о ресурсе")
public class GetListResourseTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Story("Получение информации списка ресурса")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testGetListResourse() throws Exception {
        step("Отправка GET - запроса");
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        step("Десериализация JSON - ответа в объект ResourseResponse");
        ResourseResponse resourseResponse = objectMapper.readValue(response.asString(), ResourseResponse.class);

        assertEquals(6, resourseResponse.getData().size(), "Количество пользователей не совпадает");
        step("Колличество пользователей равно 6");
        assertEquals(1, resourseResponse.getPage(), "Неверная страница");
        step("Открыта страница под №1");
        assertEquals(2, resourseResponse.getTotal_pages(), "Неверное общее количество страниц");
        step("Общее колличество страница равно 2");
        assertEquals(12, resourseResponse.getTotal(), "Неверно количество страниц");
        step("Колличество страниц равно 12");


    }
}
