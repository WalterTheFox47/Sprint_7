package scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import scooter.model.Courier;
import scooter.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient {

    @Step("Создание курьера {courier.login}")
    public Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Логин курьера {credentials.login}")
    public Response login(CourierCredentials credentials) {
        return given()
                .header("Content-type", "application/json")
                .body(credentials)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера с ID {courierId}")
    public Response delete(int courierId) {
        return given()
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
}