package practikum.yandex;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

//класс для создания и удаления курьера до и после тестов
public class CreateAndDeleteCourier {
    private final String LOGIN = "hoho"; //корректный логин
    private final String PASSWORD = "1234"; //корректный пароль
    private final String FIRST_NAME = "Alena"; //корректное имя
    private final String INCORRECT_LOGIN = "haha"; //некорректный логин
    private final String INCORRECT_PASSWORD = "4321";//некорректный пароль

    private final String BASE_URI = "http://qa-scooter.praktikum-services.ru/";

    //Геттеры для констант
    public String getIncorrectLogin() {
        return INCORRECT_LOGIN;
    }

    public String getIncorrectPassword() {
        return INCORRECT_PASSWORD;
    }

    public String getLogin() {
        return LOGIN;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public String getFirstName() {
        return FIRST_NAME;
    }

    public String getBASE_URI() {
        return BASE_URI;
    }

    CreateCourier createCourier;
    Response response;
    LoginResponse loginResponse;

    //создаем объект нового курьера
    public CreateCourier createNewCourier() {
         createCourier = new CreateCourier(LOGIN, PASSWORD, FIRST_NAME);
        return createCourier;
    }
//отправляем post запрос для создания курьера
    public void  sendPostToCreateCourier() {
        RestAssured.baseURI = BASE_URI;
        response = given().header("Content-type", "application/json")
                .and()
                .body(createNewCourier())
                .when()
                .post("/api/v1/courier");


    }

}