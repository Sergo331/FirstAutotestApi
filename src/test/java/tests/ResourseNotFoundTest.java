package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourseNotFoundTest {
    private final String BASE_URL = "https://reqres.in/api/unknown";
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test

    public void testResourseNotFound() throws Exception {
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/555")
                .then()
                .statusCode(404)
                .extract()
                .response();

        String responsebody = response.getBody().asString();
        assertEquals("{}", responsebody, "Тело ответа не является пустым");
    }
}
