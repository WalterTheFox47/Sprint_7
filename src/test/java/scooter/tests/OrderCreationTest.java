package scooter.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import scooter.client.OrderClient;
import scooter.model.Order;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest extends BaseTest {
    private OrderClient orderClient;
    private Order order;
    private List<String> color;

    public OrderCreationTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Collection<Object[]> colorData() {
        return Arrays.asList(new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {null}
        });
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = Order.getRandom();
        order.setColor(color);
    }

    @Test
    @Description("Проверка, что заказ можно создать с разными вариантами цветов самокатов. " +
            "Для каждого варианта цвет должен быть передан корректно, и ответ должен содержать track.")
    public void orderCanBeCreatedWithDifferentColorOptions() {
        Response response = orderClient.create(order);
        response.then().statusCode(201).body("track", notNullValue());
    }
}
