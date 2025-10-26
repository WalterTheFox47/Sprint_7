package scooter.tests;

import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;
import scooter.client.CourierClient;
import scooter.model.Courier;
import scooter.model.CourierCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierCreationTest extends BaseTest {
    private Courier courier;
    private CourierClient courierClient;
    private Integer courierId;

    public CourierCreationTest() {
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
    @Description("Проверка создания курьера с валидными данными. Курьер должен быть создан и ответ должен содержать ok: true.")
    public void courierCanBeCreatedWithValidData() {
        Response response = courierClient.create(courier);
        response.then().statusCode(201).body("ok", equalTo(true));
    }

    @Test
    @Description("Проверка, что нельзя создать двух одинаковых курьеров с одинаковыми данными. Должна быть ошибка с кодом 409.")
    public void cannotCreateTwoIdenticalCouriers() {
        courierClient.create(courier);
        Response response = courierClient.create(courier);
        response.then().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Description("Проверка создания курьера без логина. Должен быть возвращен ответ с ошибкой 400.")
    public void cannotCreateCourierWithoutLogin() {
        courier.setLogin(null);
        Response response = courierClient.create(courier);
        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Проверка создания курьера без пароля. Должен быть возвращен ответ с ошибкой 400.")
    public void cannotCreateCourierWithoutPassword() {
        courier.setPassword(null);
        Response response = courierClient.create(courier);
        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Проверка логина курьера с правильными данными. Должен быть возвращен id курьера.")
    public void loginAfterCreationReturnsId() {
        courierClient.create(courier);
        Response loginResponse = courierClient.login(CourierCredentials.from(courier));
        loginResponse.then().statusCode(200).body("id", notNullValue());
        courierId = loginResponse.then().extract().path("id");
    }
}
