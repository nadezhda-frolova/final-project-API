package tests;

import models.*;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.LoginSpecs.*;

public class CheckColorNameTest {
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
}
