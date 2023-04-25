package tests;

import models.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpecs.*;

public class CheckIdNameTest {
    @Test
    void checkIdNameTest() {

        step("Checking user id and name", () -> {
            UserData data = given(loginRequestSpec)
                    .when()
                    .get("/unknown/2")
                    .then()
                    .spec(loginResponseSpec)
                    .extract().as(UserData.class);
            assertEquals(2, data.getData().getId());
            assertEquals("fuchsia rose", data.getData().getName());

        });
    }
}
