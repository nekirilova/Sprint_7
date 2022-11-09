import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import practikum.yandex.OrderData;
import practikum.yandex.OrderResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {
    OrderResponse orderResponse;
    public String track;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        OrderData orderData = new OrderData();
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(orderData)
                .when()
                .post("/api/v1/orders");
        orderResponse = response.body().as(OrderResponse.class);
        track = toString(orderResponse.getTrack());
        System.out.println(track);
    }

    private String toString(int track) {
        return toString(track);
    }

    @Test
    public void sendCorrectOrderTrackReturnsOrderList() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders/track?t="+track);
        response.then().assertThat().body("track", notNullValue());
}
}