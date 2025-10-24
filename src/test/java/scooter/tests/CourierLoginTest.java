package scooter.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scooter.client.CourierClient;
import scooter.model.Courier;
import scooter.model.CourierCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    private Courier courier;
    private CourierClient courierClient;
    private Integer courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.then().extract().path("id");
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    public void courierCanLoginWithValidData() {
        Response response = courierClient.login(CourierCredentials.from(courier));
        response.then().statusCode(200).body("id", notNullValue());
    }

    @Test
    public void loginWithIncorrectPasswordReturnsError() {
        CourierCredentials wrongCredentials = new CourierCredentials(courier.getLogin(), "wrong_password");
        Response response = courierClient.login(wrongCredentials);
        response.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithIncorrectLoginReturnsError() {
        CourierCredentials wrongCredentials = new CourierCredentials("wrong_login", courier.getPassword());
        Response response = courierClient.login(wrongCredentials);
        response.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithoutLoginReturnsError() {
        CourierCredentials credentialsWithoutLogin = new CourierCredentials(null, courier.getPassword());
        Response response = courierClient.login(credentialsWithoutLogin);
        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithNonExistentUserReturnsError() {
        CourierCredentials nonExistentCredentials = new CourierCredentials("nonexistent", "nonexistent");
        Response response = courierClient.login(nonExistentCredentials);
        response.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }
}