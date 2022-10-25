package ru.praktikum_services.qa_scooter.client;

import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.qa_scooter.dto.CourierRequest;
import ru.praktikum_services.qa_scooter.dto.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    public ValidatableResponse createOrder(OrderRequest orderRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(orderRequest)
                .post("orders")
                .then();
    }

    public ValidatableResponse cancelOrder(Integer track) {
        return given()
                .spec(getDefaultRequestSpec())
                .body("{\"track\": "  + track +"}")
                .put("orders/cancel")
                .then();
    }


    public ValidatableResponse getOrderList() {
        return given()
                .spec(getDefaultRequestSpec())
                .get("orders")
                .then();

    }
}