package tests;

import models.*;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpecs.*;

public class ReqresTests {
    @Test
    void checkColorNameWithGroovyTest() {

        step("Check color's name starting from 2002 year", () -> {
            given(loginRequestSpec)
                    .when()
                    .get("/unknown")
                    .then()
                    .spec(loginResponseSpec)
                    .body("data.findAll{it.year>2002}.name.flatten()",
                            CoreMatchers.hasItems("aqua sky", "tigerlily", "blue turquoise"));
        });
    }

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
