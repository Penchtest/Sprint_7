package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.dto.CourierRequest;
import ru.praktikum_services.qa_scooter.dto.LoginRequest;
import ru.praktikum_services.qa_scooter.generator.LoginRequestGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.praktikum_services.qa_scooter.generator.CourierRequestGenerator.*;

public class CourierTest {
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

    //создание курьера
    @Test
    @DisplayName("Courier should be created")
    public void CourierShouldBeCreated() {

        //create
        CourierRequest randomCourierRequest = getRandomCourierRequest();

        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        //login
        LoginRequest loginRequest = LoginRequestGenerator.from(randomCourierRequest);

         id = courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .extract()
                .path("id");
    }

    //нельзя создать двух одинаковых курьеров
    @Test
    @DisplayName("Equal courier should not be created")
    public void equalCourierShouldNotBeCreated() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();

        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }


    @Test
    @DisplayName("Courier without password should not be created")
    public void courierWithoutPasswordShouldNotBeCreated() {
        CourierRequest randomCourierRequestWithoutPassword = getRandomCourierRequestWithoutPassword();

        courierClient.create(randomCourierRequestWithoutPassword)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Courier without login should not be created")
    public void courierWithoutLoginShouldNotBeCreated() {
        CourierRequest randomCourierRequestWithoutLogin = getRandomCourierRequestWithoutLogin();

        courierClient.create(randomCourierRequestWithoutLogin)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Courier with non-unique login should not be created")
    public void courierWithNonUniqueLoginShouldNotBeCreated(){
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        randomCourierRequest.setPassword(RandomStringUtils.randomAlphabetic(5));
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
