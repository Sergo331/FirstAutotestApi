package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.ResourseResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GerSingleResourseTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test

    public void testGetListResourse() throws Exception {
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();


        ResourseResponse resourseResponse = objectMapper.readValue(response.asString(), ResourseResponse.class);

        assertEquals(6, resourseResponse.getData().size(), "Количество пользователей не совпадает");
        assertEquals(1, resourseResponse.getPage(), "Неверная страница");
        assertEquals(2, resourseResponse.getTotal_pages(), "Неверное общее количество страниц");
        assertEquals(12, resourseResponse.getTotal(), "Неверно количество страниц");


    }
}
