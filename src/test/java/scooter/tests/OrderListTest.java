package scooter.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;
import scooter.client.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;

public class OrderListTest extends BaseTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Description("Проверка, что запрос на получение списка заказов возвращает список заказов, " +
            "и в ответе содержатся заказы. Ответ должен содержать не менее одного заказа.")
    public void getOrderListReturnsOrders() {
        Response response = orderClient.getOrderList();
        response.then().statusCode(200).body("orders", notNullValue()).body("orders.size()", greaterThan(0));
    }
}
