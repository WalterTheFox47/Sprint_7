package scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import scooter.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

    @Step("Создание заказа")
    public Response create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получение списка заказов")
    public Response getOrderList() {
        return given()
                .when()
                .get("/api/v1/orders");
    }
}