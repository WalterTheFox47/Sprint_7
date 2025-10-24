package scooter.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import scooter.client.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        orderClient = new OrderClient();
    }

    @Test
    public void getOrderListReturnsOrders() {
        Response response = orderClient.getOrderList();
        response.then().statusCode(200).body("orders", notNullValue()).body("orders.size()", greaterThan(0));
    }
}