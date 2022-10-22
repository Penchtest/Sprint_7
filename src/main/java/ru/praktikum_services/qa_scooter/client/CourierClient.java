package ru.praktikum_services.qa_scooter.client;

import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.qa_scooter.dto.CourierRequest;
import ru.praktikum_services.qa_scooter.dto.LoginRequest;

import static io.restassured.RestAssured.given;
import static ru.praktikum_services.qa_scooter.config.Config.getBaseUri;

public class CourierClient extends RestClient {
    //create
    public ValidatableResponse create(CourierRequest courierRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(courierRequest)
                .post("courier")
                .then();
    }

    //login
    public ValidatableResponse login(LoginRequest loginRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(loginRequest)
                .post("courier/login")
                .then();
    }

    //delete
    public ValidatableResponse delete(Integer id) {
        return given()
                .spec(getDefaultRequestSpec())
                .delete("courier/" + id)
                .then();
    }
}