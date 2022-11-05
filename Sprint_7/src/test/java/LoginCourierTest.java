import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import practikum.yandex.LoginCourier;
import practikum.yandex.LoginResponse;

import static io.restassured.RestAssured.given;


public class LoginCourierTest {
LoginCourier loginCourier;
CreateCourier createCourier;
LoginResponse loginResponse;

@Before
    public void setUp() {
    RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    createCourier = new CreateCourier("hoho", "1234", "Alena");
    loginCourier = new LoginCourier("hoho","1234");
    given().header("Content-type", "application/json")
            .and()
            .body(createCourier)
            .when()
            .post("/api/v1/courier");
}

 @Test
    public void loginExistingCourierReturnsStatusCode200(){
    //проверяем, что при авторизации с существующим логином и паролем в ответ приходит статус код 200
     Response response = given()
             .header("Content-type", "application/json")
             .and()
             .body(loginCourier)
             .when()
             .post("/api/v1/courier/login");
             response.then().statusCode(200);
//десериализуем полученный id курьера и записываем его как объект класса LoginResponse
     LoginResponse loginResponse = response.body()
             .as(LoginResponse.class);

    //Удаляем созданного курьера
     given().header("Content-type","application/json")
          .delete("/api/v1/courier/" + loginResponse.getId());

 }


}
