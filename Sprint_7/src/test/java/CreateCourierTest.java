import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import practikum.yandex.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class CreateCourierTest {
    CreateCourier createCourier;
    LoginCourier loginCourier;
    LoginResponse loginResponse;
    CreateResponse createResponse;
    CreateAndDeleteCourier createAndDeleteCourier = new CreateAndDeleteCourier();
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        createCourier = new CreateCourier(createAndDeleteCourier.getLogin(), createAndDeleteCourier.getPassword(), createAndDeleteCourier.getFirstName());

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
                .post("/api/v1/courier");
        createResponse = response.body().as(CreateResponse.class);
        boolean actualValue = createResponse.isTrue();
        Assert.assertTrue("The value should be true", actualValue);

    }

@Test
public void createSameCourierReturnsStatusCode409() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post("/api/v1/courier");
        response.then().statusCode(201);
    Response secondResponse = given()
            .header("Content-type", "application/json")
            .and()
            .body(createCourier)
            .when()
            .post("/api/v1/courier");
    secondResponse.then().statusCode(409);
}

    @Test
    public void createSameCourierReturnsCorrectMessage() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post("/api/v1/courier");
        response.then().statusCode(201);
        Response secondResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post("/api/v1/courier");
        secondResponse.then().assertThat().body("message", equalTo("Этот логин уже используется"));

    }
    @Test
    public void createCourierWithoutLoginReturnsStatusCode400() {
        String jsonBody = "{\"password\": \"1234\", \"firstName\": \"Alena\"}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(jsonBody)
                .when()
                .post("/api/v1/courier");
        response.then().statusCode(400);
    }

    @Test
    public void createCourierWithoutPasswordReturnsStatusCode400() {
        String jsonBody = "{\"login\": \"hoho\", \"firstName\": \"Alena\"}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(jsonBody)
                .when()
                .post("/api/v1/courier");
        response.then().statusCode(400);
    }

@After
    public void deleteCourier() {
        loginCourier = new LoginCourier(createAndDeleteCourier.getLogin(), createAndDeleteCourier.getPassword());
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login");
        loginResponse = response.body().as(LoginResponse.class);
    given().header("Content-type","application/json")
            .delete("/api/v1/courier/" + loginResponse.getId());
}
}
