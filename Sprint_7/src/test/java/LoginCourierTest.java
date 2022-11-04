import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import practikum.yandex.LoginCourier;

import static io.restassured.RestAssured.given;


public class LoginCourierTest {
LoginCourier loginCourier;
CreateCourier createCourier;

@Before
    public void setUp() {
    RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    createCourier = new CreateCourier("bobo", "1234", "Alena");
    loginCourier = new LoginCourier("bobo","1234");
    given().header("Content-type", "application/json")
            .and()
            .body(createCourier)
            .when()
            .post("/api/v1/courier");
}

 @Test
    public void loginExistingCourierReturnsStatusCode200(){
     Response response = given()
             .header("Content-type", "application/json")
             .and()
             .body(loginCourier)
             .when()
             .post("/api/v1/courier/login");
             response.then().statusCode(200);

 }

}
