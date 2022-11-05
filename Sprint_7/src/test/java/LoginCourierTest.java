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
LoginCourier loginCourierIncorrectPassword;

@Before
    public void setUp() {
    RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
   //создаем курьера перед каждым тестом
    createCourier = new CreateCourier("hoho", "1234", "Alena");

   given().header("Content-type", "application/json")
            .and()
            .body(createCourier)
            .when()
            .post("/api/v1/courier");
    //создаем объект для авторизации созданного курьера
    loginCourier = new LoginCourier("hoho","1234"); //Правильные данные для авторизации

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
 @Test
    public void loginCourierWithIncorrectPasswordReturnsStatusCode404() {
     loginCourierIncorrectPassword = new LoginCourier("hoho", "4321");//создаем объект для авторизации с
     // неправильными данными

Response response = given()
        .header("Content-type", "application/json")
        .and()
        .body(loginCourierIncorrectPassword)
        .when()
        .post("/api/v1/courier/login");
response.then().statusCode(404);
//отправляем новый запрос с корректными данными и десериализуем полученный id курьера, записываем его как объект класса LoginResponse
   Response correctResponse = given()
            .header("Content-type", "application/json")
            .and()
            .body(loginCourier)
            .when()
            .post("/api/v1/courier/login");
   LoginResponse loginResponse = correctResponse.body().as(LoginResponse.class);

     //Удаляем созданного курьера
     given().header("Content-type","application/json")
             .delete("/api/v1/courier/" + loginResponse.getId());

 }

}
