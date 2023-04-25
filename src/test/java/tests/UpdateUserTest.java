package tests;

import models.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.LoginSpecs.*;

public class UpdateUserTest {
    @Test
    void updateUserTest() {
        UserBodyModel updateUser = new UserBodyModel();
        updateUser.setName("morpheus");
        updateUser.setJob("zion resident");

        step("Update and verification user", () -> {
            UserResponseModel updateResponse = given(loginRequestSpec)
                    .body(updateUser)
                    .when()
                    .put("/users/2")
                    .then()
                    .spec(loginResponseSpec)
                    .extract().as(UserResponseModel.class);
            assertThat(updateResponse.getName()).isEqualTo("morpheus");
            assertThat(updateResponse.getJob()).isEqualTo("zion resident");
        });
    }
}
