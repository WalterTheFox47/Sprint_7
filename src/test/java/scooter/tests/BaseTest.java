package scooter.tests;

import io.restassured.RestAssured;
import org.junit.Before;

public class BaseTest {

    @Before
    public void setUpBaseURI() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
}
