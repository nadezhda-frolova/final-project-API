package tests;

import models.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.LoginSpecs.*;

public class CreateUserTest {
    @Test
    void createUserTest() {
        UserBodyModel createUser = new UserBodyModel();
        createUser.setName("morpheus");
        createUser.setJob("leader");

        step("Creation and verification user", () -> {
            UserResponseModel createResponse = given(loginRequestSpec)
                    .body(createUser)
                    .when()
                    .post("/users")
                    .then()
                    .spec(createResponseSpec)
                    .extract().as(UserResponseModel.class);
            assertThat(createResponse.getName()).isEqualTo("morpheus");
            assertThat(createResponse.getJob()).isEqualTo("leader");
        });
    }
}
