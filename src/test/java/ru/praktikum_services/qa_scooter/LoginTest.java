package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.dto.CourierRequest;
import ru.praktikum_services.qa_scooter.dto.LoginRequest;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.praktikum_services.qa_scooter.generator.CourierRequestGenerator.getRandomCourierRequest;

public class LoginTest {
    private CourierClient courierClient;
    private Integer id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (id != null)
            courierClient.delete(id)
                    .assertThat()
                    .body("ok", equalTo(true));
    }


    @Test
    @DisplayName("Courier with correct data should authorize")
    public void CourierWithCorrectDataShouldAuthorize() {

        //create
        CourierRequest randomCourierRequest = getRandomCourierRequest();

        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        //login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(randomCourierRequest.getLogin());
        loginRequest.setPassword(randomCourierRequest.getPassword());

         id = courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .extract()
                .path("id");
    }


    //этот тест падает, так как возвращается 504 ошибка, вместо заявленной в документации 400
    @Test
    @DisplayName("Courier with wrong password should not authorize")
    public void CourierWithWrongPasswordShouldNotAuthorize() {

        //create
        CourierRequest randomCourierRequest = getRandomCourierRequest();

        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(randomCourierRequest.getLogin());
        loginRequest.setPassword(RandomStringUtils.randomAlphabetic(5));

        courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Courier with wrong login should not authorize")
    public void CourierWithWrongLoginShouldNotAuthorize() {

        //create
        CourierRequest randomCourierRequest = getRandomCourierRequest();

        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(RandomStringUtils.randomAlphabetic(5));
        loginRequest.setPassword(randomCourierRequest.getPassword());

        courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Courier without login should not authorize")
    public void CourierWithoutLoginShouldNotAuthorize() {

        //create
        CourierRequest randomCourierRequest = getRandomCourierRequest();

        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(randomCourierRequest.getPassword());

        courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));


    }

    @Test
    @DisplayName("Courier without password should not authorize")
    public void CourierWithoutPasswordShouldNotBeAuthorize() {

        //create
        CourierRequest randomCourierRequest = getRandomCourierRequest();

        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(randomCourierRequest.getLogin());

        courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));


    }

    @Test
    @DisplayName("Courier without registration should not authorize")
    public void CourierWithoutRegistrationShouldNotAuthorize() {

        //create
        CourierRequest randomCourierRequest = getRandomCourierRequest();

        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(RandomStringUtils.randomAlphabetic(5));
        loginRequest.setPassword(RandomStringUtils.randomAlphabetic(5));

        courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));


    }
}