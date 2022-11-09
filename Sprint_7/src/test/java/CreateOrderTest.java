import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import practikum.yandex.CreateOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    //добавляем поля класса для параметров тестового метода
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String color;
//создаем конструктор для тестовых данных
    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    //добавляем метод для получения параметризованных тестовых данных:
    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][] {
                {"Max", "Maximov", "Lenina st.,15-58", "22", "89008007060", 5, "2022-11-08", "", "BLACK"}
        };
    }
CreateOrder createOrder;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        CreateOrder createOrder = new CreateOrder(firstName, lastName, address,metroStation,
                phone, rentTime, deliveryDate, comment,color);
    }
    @Test
    public void createOrderWithCorrectDataReturnsTrackNumber() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createOrder)
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue());
    //    System.out.println(response.body());
    }
}
