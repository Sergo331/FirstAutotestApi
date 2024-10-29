package teasts;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.UserData;
import models.UsersResponse;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetListUsersTests {
    private final String BASE_URL = "https://reqres.in/api/users";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test

public void testGetListUsers() throws Exception {
        Response response = RestAssured
                .given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();


        UsersResponse usersResponse = objectMapper.readValue(response.asString(), UsersResponse.class);

        assertEquals(6, usersResponse.getData().size(), "Количество пользователей не совпадает");
        assertEquals(1, usersResponse.getPage(), "Неверная страница");
        assertEquals(2, usersResponse.getTotal_pages(), "Неверное общее количество страниц");
        assertEquals(12, usersResponse.getTotal(), "Неверно количество страниц");

        for (UserData user : usersResponse.getData()) {
            assertTrue(user.getEmail().endsWith("@reqres.in"), "Email пользователя" + user.getId() + "не оканчивается на reqres.in");
        }



    }


}
