package ru.praktikum_services.qa_scooter.generator;


import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import ru.praktikum_services.qa_scooter.dto.OrderRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;


public class OrderRequestGenerator {
    //приведение даты к нужному формату
    private static String getISO8601StringForDate (Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }


    public static OrderRequest getRandomOrderRequest(){
        Random random = new Random();
        Faker faker = new Faker();
        Date date = faker.date().between(new Date(2022, 10, 15), new Date(2022, 11, 15));
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setFirstName(faker.name().firstName());
        orderRequest.setLastName(faker.name().lastName());
        orderRequest.setAddress(faker.address().fullAddress());
        orderRequest.setMetroStation(Integer.toString((random.nextInt(236))+1));
        orderRequest.setPhone(faker.numerify("8##########"));
        orderRequest.setRentTime(random.nextInt(6)+1);
        orderRequest.setDeliveryDate(getISO8601StringForDate(date));
        orderRequest.setComment((RandomStringUtils.randomAlphanumeric(10)));
        return orderRequest;
    }
}
