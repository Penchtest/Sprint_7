package ru.praktikum_services.qa_scooter.generator;

import org.apache.commons.lang3.RandomStringUtils;
import ru.praktikum_services.qa_scooter.dto.CourierRequest;

public class CourierRequestGenerator {
    public static CourierRequest getRandomCourierRequest(){
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setFirstName(RandomStringUtils.randomAlphabetic(5));
        courierRequest.setPassword(RandomStringUtils.randomAlphabetic(5));
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(5));
        return courierRequest;
    }

    public static CourierRequest getRandomCourierRequestWithoutPassword(){
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setFirstName(RandomStringUtils.randomAlphabetic(5));
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(5));
        return courierRequest;
    }

    public static CourierRequest getRandomCourierRequestWithoutLogin(){
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setFirstName(RandomStringUtils.randomAlphabetic(5));
        courierRequest.setPassword(RandomStringUtils.randomAlphabetic(5));
        return courierRequest;
    }
}
