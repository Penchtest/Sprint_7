package ru.praktikum_services.qa_scooter.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.praktikum_services.qa_scooter.config.Config;

import static ru.praktikum_services.qa_scooter.config.Config.getBaseUri;

public class RestClient {
    public RequestSpecification getDefaultRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(Config.getBaseUri())
                .setContentType(ContentType.JSON)
                .build();


    }
}
