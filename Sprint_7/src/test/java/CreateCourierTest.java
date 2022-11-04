import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreateCourierTest {
    CreateCourier createCourier;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        createCourier = new CreateCourier("alenushka", "1234", "Alena");
    }

    @Test
    public void createNewCourierReturnsStatusCode201() {
    Response response = given()
            .header("Content-type", "application/json")
            .and()
            .body(createCourier)
            .when()
            .post("/api/v1/courier");
            response.then().statusCode(201);
    }

//    @Test
//    public void createDoubleCourierReturnsStatusCode409() {
//
//    }

//    @After
//    public void deleteCourier() {
//
//    }
}
