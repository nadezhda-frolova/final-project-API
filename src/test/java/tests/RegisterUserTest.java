package tests;

import models.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.LoginSpecs.*;

public class RegisterUserTest {
    @Test
    void registerUserTest() {
        RegisterBodyModel registerBody = new RegisterBodyModel();
        registerBody.setEmail("eve.holt@reqres.in");
        registerBody.setPassword("pistol");

        step("Register and verification user", () -> {
            RegisterResponseModel registerResponse = given(loginRequestSpec)
                    .body(registerBody)
                    .when()
                    .post("/register")
                    .then()
                    .spec(loginResponseSpec)
                    .extract().as(RegisterResponseModel.class);
            assertThat(registerResponse.getId()).isEqualTo(4);
            assertThat(registerResponse.getToken()).isNotNull();
        });
    }
}
