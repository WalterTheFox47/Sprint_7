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

public class CourierCreationTest {
    private Courier courier;
    private CourierClient courierClient;
    private Integer courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    public void courierCanBeCreatedWithValidData() {
        Response response = courierClient.create(courier);
        response.then().statusCode(201).body("ok", equalTo(true));
    }

    @Test
    public void cannotCreateTwoIdenticalCouriers() {
        courierClient.create(courier);
        Response response = courierClient.create(courier);
        response.then().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void cannotCreateCourierWithoutLogin() {
        courier.setLogin(null);
        Response response = courierClient.create(courier);
        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void cannotCreateCourierWithoutPassword() {
        courier.setPassword(null);
        Response response = courierClient.create(courier);
        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void loginAfterCreationReturnsId() {
        courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCredentials.from(courier));
        loginResponse.then().statusCode(200).body("id", notNullValue());
        courierId = loginResponse.then().extract().path("id");
    }
}