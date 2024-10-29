package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.SingleResourseResponse;
import org.junit.jupiter.api.Test;

public class GetSingleResourseTests{
    private final String BASE_URL = "https://reqres.in/api/unknown/2";
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


        SingleResourseResponse singleResourseResponse = objectMapper.readValue(response.asString(), SingleResourseResponse.class);
    }
}
