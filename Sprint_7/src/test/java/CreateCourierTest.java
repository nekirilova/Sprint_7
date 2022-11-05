import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import practikum.yandex.CreateAndDeleteCourier;
import practikum.yandex.CreateCourier;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class CreateCourierTest {
    CreateCourier createCourier;
    CreateAndDeleteCourier createAndDeleteCourier;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        createCourier = new CreateCourier("alla", "1234", "Alena");
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

    @Test
    public void createCourierSuccessfullyReturnsCorrectResponse() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post("/api/vi/courier");
        response.then().assertThat().body("ok", equalTo(true));
    }

//    @After
//    public void deleteCourier() {
//
//    }
}
