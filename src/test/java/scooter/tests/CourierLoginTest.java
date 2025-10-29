package scooter.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scooter.client.CourierClient;
import scooter.model.Courier;
import scooter.model.CourierCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest extends BaseTest {
    private Courier courier;
    private CourierClient courierClient;
    private Integer courierId;

    @Before
    public void setUp() {
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
    @Description("Проверка, что курьер может авторизоваться с валидными данными. Должен быть возвращен id.")
    public void courierCanLoginWithValidData() {
        Response response = courierClient.login(CourierCredentials.from(courier));
        response.then().statusCode(200).body("id", notNullValue());
    }

    @Test
    @Description("Проверка, что при неправильном пароле для логина возвращается ошибка 404 с сообщением 'Учетная запись не найдена'.")
    public void loginWithIncorrectPasswordReturnsError() {
        CourierCredentials wrongCredentials = new CourierCredentials(courier.getLogin(), "wrong_password");
        Response response = courierClient.login(wrongCredentials);
        response.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Description("Проверка, что при неправильном логине для авторизации возвращается ошибка 404 с сообщением 'Учетная запись не найдена'.")
    public void loginWithIncorrectLoginReturnsError() {
        CourierCredentials wrongCredentials = new CourierCredentials("wrong_login", courier.getPassword());
        Response response = courierClient.login(wrongCredentials);
        response.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Description("Проверка, что при отсутствии логина в запросе на авторизацию возвращается ошибка 400 с сообщением 'Недостаточно данных для входа'.")
    public void loginWithoutLoginReturnsError() {
        CourierCredentials credentialsWithoutLogin = new CourierCredentials(null, courier.getPassword());
        Response response = courierClient.login(credentialsWithoutLogin);
        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Проверка, что при попытке авторизоваться с несуществующим пользователем возвращается ошибка 404 с сообщением 'Учетная запись не найдена'.")
    public void loginWithNonExistentUserReturnsError() {
        CourierCredentials nonExistentCredentials = new CourierCredentials("nonexistent", "nonexistent");
        Response response = courierClient.login(nonExistentCredentials);
        response.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }
}
